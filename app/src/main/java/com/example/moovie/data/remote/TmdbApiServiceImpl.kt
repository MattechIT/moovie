package com.example.moovie.data.remote

import com.example.moovie.BuildConfig
import com.example.moovie.data.model.Movie
import com.example.moovie.data.repository.PreferenceRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.first
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * API response container matching TMDB discover response structure.
 */
@Serializable
private data class TmdbResponse(
    val page: Int,
    val results: List<Movie>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

/**
 * Ktor implementation of the TmdbApiService.
 */
class TmdbApiServiceImpl(
    private val httpClient: HttpClient,
    private val preferenceRepository: PreferenceRepository
) : TmdbApiService {

    private companion object {
        const val API_KEY = BuildConfig.TMDB_API_KEY
    }

    override fun isApiKeyConfigured(): Boolean {
        return API_KEY.isNotBlank() && !API_KEY.startsWith("YOUR_TMDB")
    }

    override suspend fun getMoviesByGenres(genresParam: String, page: Int): List<Movie> {
        val langCode = preferenceRepository.appLanguage.first().localeCode
        val response = httpClient.get("https://api.themoviedb.org/3/discover/movie") {
            parameter("api_key", API_KEY)
            parameter("with_genres", genresParam)
            parameter("language", langCode)
            parameter("sort_by", "popularity.desc")
            parameter("include_adult", "false")
            parameter("vote_count.gte", "300")
            parameter("with_original_language", "en|it|fr|es")
            parameter("page", page.toString())
        }.body<TmdbResponse>()
        return response.results
    }

    override suspend fun getMovieById(movieId: Int): Movie {
        val langCode = preferenceRepository.appLanguage.first().localeCode
        return httpClient.get("https://api.themoviedb.org/3/movie/$movieId") {
            parameter("api_key", API_KEY)
            parameter("language", langCode)
            parameter("append_to_response", "credits")
        }.body<Movie>()
    }

    override suspend fun searchMovies(query: String): List<Movie> {
        val langCode = preferenceRepository.appLanguage.first().localeCode
        val response = httpClient.get("https://api.themoviedb.org/3/search/movie") {
            parameter("api_key", API_KEY)
            parameter("query", query)
            parameter("language", langCode)
            parameter("include_adult", "false")
            parameter("page", "1")
        }.body<TmdbResponse>()
        return response.results
    }
}
