package com.example.moovie.data.repository

import com.example.moovie.data.model.Cinema

/**
 * Static catalog containing mockup local cinemas data mapped with dynamic movie IDs.
 */
object MockCinemaCatalog {

    /**
     * Generate list of mock cinemas using active movie IDs representing the active catalog.
     */
    fun getMockCinemas(happyMovieIds: List<Int>, energeticMovieIds: List<Int>): List<Cinema> {
        val allMovieIds = happyMovieIds + energeticMovieIds
        return listOf(
            Cinema(
                id = 1,
                name = "Cinema Ducale",
                latitude = 45.452398,
                longitude = 9.1546299,
                address = "Piazza Napoli",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "http://cinenauta.it/ducale/"
            ),
            Cinema(
                id = 2,
                name = "The Space Cinema",
                latitude = 42.5218808,
                longitude = 14.1503445,
                address = "Viale Alberto D'Andrea, 1, Montesilvano",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.thespacecinema.it/al-cinema/montesilvano"
            ),
            Cinema(
                id = 3,
                name = "Cinema Multiplex Arca",
                latitude = 42.4567888,
                longitude = 14.1785708,
                address = "Via Federico Fellini, 2, Spoltore",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://multicinema.it/Programmazione?cinema=arca"
            ),
            Cinema(
                id = 4,
                name = "Uci Cinemas Bicocca",
                latitude = 45.5219373,
                longitude = 9.2161433,
                address = "Viale Sarca, 336, Milano",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.ucicinemas.it/cinema/lombardia/milano/uci-cinemas-bicocca-milano/"
            ),
            Cinema(
                id = 5,
                name = "Multiastra",
                latitude = 45.4208343,
                longitude = 11.8805344,
                address = "Via Tiziano Aspetti, 21, Padova",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.multiastra.it/"
            ),
            Cinema(
                id = 6,
                name = "Quattro Fontane",
                latitude = 41.9013587,
                longitude = 12.4920263,
                address = "Via delle Quattro Fontane, 23, Roma",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.circuitocinema.com/cinema/quattro-fontane"
            ),
            Cinema(
                id = 7,
                name = "The Space Cinema Odeon",
                latitude = 45.4651207,
                longitude = 9.192365,
                address = "Via Santa Radegonda, 8, Milano",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.thespacecinema.it/al-cinema/milano-odeon"
            ),
            Cinema(
                id = 8,
                name = "Madison",
                latitude = 41.853415,
                longitude = 12.4793038,
                address = "Via Gabriello Chiabrera, 121, Roma",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cinemamadison.it/"
            ),
            Cinema(
                id = 9,
                name = "Multisala Atlantic",
                latitude = 41.8607808,
                longitude = 12.555606,
                address = "Via Tuscolana, 745, Roma",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.ferrerocinemas.it/index.php?id_menu=37"
            ),
            Cinema(
                id = 10,
                name = "Gloria",
                latitude = 45.4665478,
                longitude = 9.1618614,
                address = "Corso Vercelli, 18, Milano",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.notoriouscinemas.it/gloria/index.php"
            ),
            Cinema(
                id = 11,
                name = "Orfeo Multisala",
                latitude = 45.4569421,
                longitude = 9.1695263,
                address = "Viale Coni Zugna, 50, Milano",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.orfeomultisala.com/"
            ),
            Cinema(
                id = 12,
                name = "Al Politeama Multisala",
                latitude = 38.1246772,
                longitude = 13.35697,
                address = "Via Emerico Amari, 160, Palermo",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://alpoliteama.it/"
            ),
            Cinema(
                id = 13,
                name = "Royal",
                latitude = 41.8898212,
                longitude = 12.5067479,
                address = "Via Emanuele Filiberto, 175, Roma",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.ferrerocinemas.it/royal/index.php"
            ),
            Cinema(
                id = 14,
                name = "Cinema Ariston",
                latitude = 38.136838,
                longitude = 13.345546,
                address = "Via Luigi Pirandello, 5, Palermo",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "http://www.arlecchinoariston.it/"
            ),
            Cinema(
                id = 15,
                name = "Savoy Cityplex",
                latitude = 41.9113102,
                longitude = 12.4995899,
                address = "Via Bergamo, 25, Roma",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.mycityplex.it/savoy/"
            ),
            Cinema(
                id = 16,
                name = "Cinema Adriano",
                latitude = 41.9060169,
                longitude = 12.4697046,
                address = "Piazza Cavour, 22",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "http://www.ferrerocinemas.it/adriano/index.php#inside"
            ),
            Cinema(
                id = 17,
                name = "Kinemax Gorizia / Gorica",
                latitude = 45.9455772,
                longitude = 13.6256299,
                address = "Piazza della Vittoria, 41",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.kinemax.it/"
            ),
            Cinema(
                id = 18,
                name = "Nuovo Cinema Pasubio",
                latitude = 45.7149542,
                longitude = 11.3439244,
                address = "Via Pietro Maraschin, 81, Schio",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinemapasubio.it"
            ),
            Cinema(
                id = 19,
                name = "Cineland",
                latitude = 41.7462591,
                longitude = 12.2869099,
                address = "Viale dei Romagnoli, 515, Roma",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "http://cineland.it/"
            ),
            Cinema(
                id = 20,
                name = "Arcobaleno Film Center",
                latitude = 45.4770752,
                longitude = 9.2056724,
                address = "Viale Tunisia, 11, Milano",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "http://cinenauta.it/arcobaleno/"
            ),
            Cinema(
                id = 21,
                name = "Eliseo",
                latitude = 45.4608342,
                longitude = 9.1826282,
                address = "Via Torino, 64, Milano",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.ilregnodelcinema.com/eliseo/infocinema.php"
            ),
            Cinema(
                id = 22,
                name = "Cinema Nuovo Pendola",
                latitude = 43.3148135,
                longitude = 11.3281863,
                address = "Via di San Quirico, 13, Siena",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinemanuovopendola.it/"
            ),
            Cinema(
                id = 23,
                name = "Movie Planet",
                latitude = 45.3236406,
                longitude = 8.4166692,
                address = "Piazza Pietro Pajetta (Nedo), 3/5, Vercelli",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.movieplanetgroup.it/mp_italia/index.php"
            ),
            Cinema(
                id = 24,
                name = "Metropolis cinema multisala",
                latitude = 45.7430007,
                longitude = 11.7355944,
                address = "Via Cristoforo Colombo, 84, Bassano del Grappa",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.metropoliscinemas.it/"
            ),
            Cinema(
                id = 25,
                name = "Multisala Grotta",
                latitude = 43.8337699,
                longitude = 11.2009782,
                address = "Via Antonio Gramsci, 387, Sesto Fiorentino",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "http://www.grotta.it/"
            ),
            Cinema(
                id = 26,
                name = "Cinema Gaudium",
                latitude = 38.1305287,
                longitude = 13.345148,
                address = "Via Damiani Almeyda, 32, Palermo",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinemagaudium.it/"
            ),
            Cinema(
                id = 27,
                name = "Cinema Teatro Golden",
                latitude = 38.1296262,
                longitude = 13.3448487,
                address = "Via Terrasanta, 60",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemateatrogolden.it/"
            ),
            Cinema(
                id = 28,
                name = "Cinema Teatro Verdi",
                latitude = 40.7047125,
                longitude = 17.3395439,
                address = "Piazza XX Settembre, 5, Martina Franca",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.teatroverdi.eu/"
            ),
            Cinema(
                id = 29,
                name = "Multisala Diana",
                latitude = 44.3107358,
                longitude = 8.4787927,
                address = "Via Giuseppe Brignoni, 1r, Savona",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "http://www.multisaladiana.it/"
            ),
            Cinema(
                id = 30,
                name = "Cinema Lux",
                latitude = 45.3909316,
                longitude = 11.8705986,
                address = "Viale Felice Cavallotti, 9, Padova",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "http://cinemaluxpadova.it"
            ),
            Cinema(
                id = 31,
                name = "Cinema Multisala Marconi",
                latitude = 38.1068999,
                longitude = 13.3408104,
                address = "Via Cuba, 12, Palermo",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemarconipalermo.it/"
            ),
            Cinema(
                id = 32,
                name = "Multiplex Omnia Center",
                latitude = 43.8740377,
                longitude = 11.0630448,
                address = "Via delle Pleiadi, 16, Prato",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.giomettirealestatecinema.it/cinema/multiplex-omnia-center-prato/programmazione"
            ),
            Cinema(
                id = 33,
                name = "Cinema Esedra",
                latitude = 41.1185756,
                longitude = 16.8832022,
                address = "Largo Monsignore Augusto Curi, 19",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "http://www.cinemaesedra.it/"
            ),
            Cinema(
                id = 34,
                name = "Multiplex delle Stelle",
                latitude = 42.8582282,
                longitude = 13.7096668,
                address = "Viale dei Mutilati e Invalidi del Lavoro, 106, Campolungo",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.multiplexdellestelle.it/"
            ),
            Cinema(
                id = 35,
                name = "Cineteatro Excelsior",
                latitude = 45.6072048,
                longitude = 9.2416287,
                address = "Via don Carlo Colnaghi, 3, Lissone",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "http://www.excelsior-lissone.it/"
            ),
            Cinema(
                id = 36,
                name = "Cinema City",
                latitude = 44.4181358,
                longitude = 12.1713082,
                address = "Via Secondo Bini, 7",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cinemacity.it/"
            ),
            Cinema(
                id = 37,
                name = "Ambra Multiplex",
                latitude = 41.6894061,
                longitude = 12.7889412,
                address = "Viale Filippo Turati, 5, Velletri",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.ambracinema.com/"
            ),
            Cinema(
                id = 38,
                name = "Armida",
                latitude = 40.626421,
                longitude = 14.3777921,
                address = "Corso Italia, 217, Sorrento",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "http://www.armida.eu/"
            ),
            Cinema(
                id = 39,
                name = "UCI Cinemas Certosa",
                latitude = 45.5096401,
                longitude = 9.1281504,
                address = "Via Giovanni Gentile, 3, Milano",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.ucicinemas.it/cinema/lombardia/milano/uci-cinemas-certosa-milano/"
            ),
            Cinema(
                id = 40,
                name = "Cinema Teatro Galleria",
                latitude = 45.5939779,
                longitude = 8.9179552,
                address = "Galleria INA, Legnano",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "http://www.cinemateatrogalleria.it/"
            ),
            Cinema(
                id = 41,
                name = "Supercinema 2.0",
                latitude = 41.4631368,
                longitude = 12.9046949,
                address = "Corso della Repubblica, 279, Latina",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinemalatina.it/supercinema-2-0.html"
            ),
            Cinema(
                id = 42,
                name = "Multisala Corso",
                latitude = 41.4663865,
                longitude = 12.9037266,
                address = "Corso della Repubblica, 148, Latina",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinemalatina.it/multisala-corso.html"
            ),
            Cinema(
                id = 43,
                name = "Visionario",
                latitude = 46.0647909,
                longitude = 13.2291118,
                address = "Via Fabio Asquini, 33",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://visionario.movie/"
            ),
            Cinema(
                id = 44,
                name = "The Space Cinema",
                latitude = 39.2450833,
                longitude = 9.1627547,
                address = "Via delle Serre, Quartucciu",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.thespacecinema.it/al-cinema/quartucciu"
            ),
            Cinema(
                id = 45,
                name = "Cinema Ratti",
                latitude = 45.5941968,
                longitude = 8.9184139,
                address = "Corso Magenta, 9, Legnano",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://cinemasalaratti.wordpress.com/"
            ),
            Cinema(
                id = 46,
                name = "Multiplex Giometti Ancona",
                latitude = 43.5625792,
                longitude = 13.5120524,
                address = "Via Pietro Filonzi, 4, Ancona",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.giomettirealestatecinema.it/cinema/multiplex-ancona/programmazione"
            ),
            Cinema(
                id = 47,
                name = "Cinema Olimpia",
                latitude = 44.6823757,
                longitude = 10.626574,
                address = "Via Alessandro Tassoni, 4, Reggio Emilia",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemaolimpia.com/"
            ),
            Cinema(
                id = 48,
                name = "Cinema Orione",
                latitude = 44.5015083,
                longitude = 11.3132902,
                address = "Via Cimabue, 14, Bologna",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.orionecineteatro.it/"
            ),
            Cinema(
                id = 49,
                name = "Movieland Goldoni",
                latitude = 43.6158688,
                longitude = 13.5131994,
                address = "Via Montebello, 64, Ancona",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://ancona.movieland.18tickets.it/"
            ),
            Cinema(
                id = 50,
                name = "Cinema Multisala Cynthianum",
                latitude = 41.7111303,
                longitude = 12.6898291,
                address = "Viale Giuseppe Mazzini, 9, Genzano di Roma",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinemagenzano.it/"
            ),
            Cinema(
                id = 51,
                name = "Cinema Europa",
                latitude = 41.9101282,
                longitude = 12.5006645,
                address = "Corso d'Italia, 107/a, Roma",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.mycityplex.it/europa/index.php"
            ),
            Cinema(
                id = 52,
                name = "Cinema Saffi D'Essai",
                latitude = 44.1869835,
                longitude = 12.0350612,
                address = "Viale dell'Appennino, 480, Forlì",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cinemasaffi.com/"
            ),
            Cinema(
                id = 53,
                name = "Centrale",
                latitude = 46.0616254,
                longitude = 13.2325812,
                address = "Via Poscolle, 8",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://visionario.movie/"
            ),
            Cinema(
                id = 54,
                name = "Multisala Astoria",
                latitude = 44.2042261,
                longitude = 12.0289953,
                address = "Viale dell'Appennino, 313, Forlì",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinemaastoria.it/"
            ),
            Cinema(
                id = 55,
                name = "UCI",
                latitude = 45.0332344,
                longitude = 7.6660871,
                address = "Via Nizza, 230",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.ucicinemas.it/"
            ),
            Cinema(
                id = 56,
                name = "Movieland Fabriano",
                latitude = 43.3475881,
                longitude = 12.922566,
                address = "Viale Beniamino Gigli, 19, Fabriano",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://fabriano.movieland.18tickets.it/"
            ),
            Cinema(
                id = 57,
                name = "The Space Cinema - Moderno Roma",
                latitude = 41.902004,
                longitude = 12.4964182,
                address = "Piazza della Repubblica, 45, Roma",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.thespacecinema.it/al-cinema/roma-moderno"
            ),
            Cinema(
                id = 58,
                name = "Cinema Multisala Apollo Cinepark",
                latitude = 44.8326787,
                longitude = 11.6195356,
                address = "Via del Carbone, 35, Ferrara",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.apollocinepark.it"
            ),
            Cinema(
                id = 59,
                name = "Cinema Azzurro",
                latitude = 43.6122833,
                longitude = 13.5318064,
                address = "Via Tagliamento, 35, Ancona",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "http://azzurrocinema.altervista.org/"
            ),
            Cinema(
                id = 60,
                name = "Pop Up Cinema Medica 4K",
                latitude = 44.4958632,
                longitude = 11.3406775,
                address = "Via Monte Grappa, 9/b/c/d",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://medica.popupcinema.18tickets.it/"
            ),
            Cinema(
                id = 61,
                name = "Azzurro Scipioni",
                latitude = 41.9084895,
                longitude = 12.4582398,
                address = "Via degli Scipioni, 82, Roma",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.azzurroscipioni.com/"
            ),
            Cinema(
                id = 62,
                name = "Cinema Teatro Nuovo",
                latitude = 45.8242719,
                longitude = 8.8352137,
                address = "Viale dei Mille, 39, Varese",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.filmstudio90.it/cinema-teatro-nuovo-varese/"
            ),
            Cinema(
                id = 63,
                name = "Cinema Teatro Italia",
                latitude = 43.3001813,
                longitude = 13.4518173,
                address = "Via Antonio Gramsci, 25",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemaitaliamacerata.it/"
            ),
            Cinema(
                id = 64,
                name = "Cinema Greenwich d'essai",
                latitude = 39.2171694,
                longitude = 9.110896,
                address = "Via Sassari, 67, Cagliari",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "http://www.greenwichdessai.org/"
            ),
            Cinema(
                id = 65,
                name = "Multiplex Giometti",
                latitude = 43.2183637,
                longitude = 13.308296,
                address = "Contrada Pace, Tolentino",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.giomettirealestatecinema.it/cinema/multiplex-tolentino/programmazione"
            ),
            Cinema(
                id = 66,
                name = "Cinema Verdi",
                latitude = 43.1026509,
                longitude = 10.5392474,
                address = "Via Vittorio Emanuele II, 121, San Vincenzo",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "http://cinemateatroverdi.altervista.org"
            ),
            Cinema(
                id = 67,
                name = "Cinema Italia Eden",
                latitude = 45.7788932,
                longitude = 12.0444073,
                address = "Viale della Vittoria, 31, Montebelluna",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemamontebelluna.com/"
            ),
            Cinema(
                id = 68,
                name = "Notorious Cinemas Cagliari",
                latitude = 39.2272644,
                longitude = 9.0977525,
                address = "Via Santa Gilla, 18, Cagliari",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.notoriouscinemas.it/cagliari/index.php"
            ),
            Cinema(
                id = 69,
                name = "Blue Torino",
                latitude = 45.0597982,
                longitude = 7.6828771,
                address = "Via Principe Tommaso, 6, Torino",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.circuitograntorino.com/"
            ),
            Cinema(
                id = 70,
                name = "San Carlo",
                latitude = 45.2215856,
                longitude = 10.4135705,
                address = "Via Libertà, 1, Asola",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinemasancarlo.it/"
            ),
            Cinema(
                id = 71,
                name = "Cineclub Detour",
                latitude = 41.8962008,
                longitude = 12.493962,
                address = "Via Urbana, 107, Roma",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinedetour.it/"
            ),
            Cinema(
                id = 72,
                name = "The Space Cinema",
                latitude = 44.511329,
                longitude = 11.3728853,
                address = "Viale Europa, 5, Bologna",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.thespacecinema.it/al-cinema/bologna"
            ),
            Cinema(
                id = 73,
                name = "Pop Up Cinema Arlecchino",
                latitude = 44.4992572,
                longitude = 11.3353131,
                address = "Via delle Lame, 59a, Bologna",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://arlecchino.popupcinema.18tickets.it/"
            ),
            Cinema(
                id = 74,
                name = "Happy MaxiCinema",
                latitude = 40.9340168,
                longitude = 14.358754,
                address = "Afragola",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://happy.stellafilm.stellafilm.it/"
            ),
            Cinema(
                id = 75,
                name = "Cinema Margherita",
                latitude = 43.0245908,
                longitude = 13.8578771,
                address = "Via Cavour, 23, Cupra Marittima",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemamargherita.com/"
            ),
            Cinema(
                id = 76,
                name = "Cinema Lumière",
                latitude = 44.5023982,
                longitude = 11.3342452,
                address = "Piazza Pierpaolo Pasolini, 3b, Bologna",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://cinetecadibologna.it/luogo/cinema-lumiere/"
            ),
            Cinema(
                id = 77,
                name = "C'entro - Supercinema Santarcangelo",
                latitude = 44.0619387,
                longitude = 12.4451517,
                address = "Piazza Guglielmo Marconi, 1, Santarcangelo di Romagna",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.centrosupercinema.com/"
            ),
            Cinema(
                id = 78,
                name = "Cinema Splendor",
                latitude = 45.0117969,
                longitude = 7.8243336,
                address = "Via Venti Settembre, 6, Chieri",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinema-splendor.it/"
            ),
            Cinema(
                id = 79,
                name = "Kino im Bahnhof",
                latitude = 46.417842,
                longitude = 11.2509636,
                address = "Bahnhofstraße - Via Stazione, 3",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.filmtreff-kaltern.it/"
            ),
            Cinema(
                id = 80,
                name = "Circolo Cinematografico Agorà",
                latitude = 43.6630334,
                longitude = 10.6357343,
                address = "Via Valtriani, 20, Pontedera",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "http://circolocinematograficoagora.blogspot.it/"
            ),
            Cinema(
                id = 81,
                name = "Cinema Splendor Boaro",
                latitude = 45.4671796,
                longitude = 7.8792445,
                address = "Via Palestro, 84, Ivrea",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinemasplendorboaro.it/index.html"
            ),
            Cinema(
                id = 82,
                name = "Multisala Saronnese",
                latitude = 45.6271894,
                longitude = 9.033488,
                address = "Via San Giuseppe, 21, Saronno",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.multisalasaronnese.it/"
            ),
            Cinema(
                id = 83,
                name = "Cinelandia",
                latitude = 44.3471538,
                longitude = 7.5064258,
                address = "Via Cuneo, 86",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinelandia.it/"
            ),
            Cinema(
                id = 84,
                name = "Cinema Metropolitan",
                latitude = 42.9233461,
                longitude = 10.5277281,
                address = "Piazza Licurgo Cappeletti, 2, Piombino",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.metropolitanpiombino.it"
            ),
            Cinema(
                id = 85,
                name = "Cineteca Milano Meet",
                latitude = 45.4753723,
                longitude = 9.2039666,
                address = "Viale Vittorio Veneto, 2, Milano",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinetecamilano.it/location/milano-meet"
            ),
            Cinema(
                id = 86,
                name = "Giometti Cinema Pesaro",
                latitude = 43.9045573,
                longitude = 12.8673422,
                address = "Piazza Marcello Stefanini, 15, Pesaro",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.ucicinemas.it/uci_pesaro/index.php/"
            ),
            Cinema(
                id = 87,
                name = "Cinema Multisala Lux",
                latitude = 43.9308197,
                longitude = 10.9148162,
                address = "Corso Antonio Gramsci, 3, Pistoia",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.staseraalcinema.it/multisala-lux/"
            ),
            Cinema(
                id = 88,
                name = "Cinepark Cinema Multisala",
                latitude = 44.7482109,
                longitude = 11.3034407,
                address = "Via Matteo Loves, 17, Cento",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cinepark.it/5105/"
            ),
            Cinema(
                id = 89,
                name = "Multiplex Fasano",
                latitude = 39.9813934,
                longitude = 18.0880557,
                address = "Via Donatori Volontari Sangue, 1",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://multiplexteatrofasano.it"
            ),
            Cinema(
                id = 90,
                name = "Notorious Cinemas - Rovigo",
                latitude = 45.0282863,
                longitude = 11.7774108,
                address = "Viale Porta Po, 209, Rovigo",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.notoriouscinemas.it/rovigo/index.php"
            ),
            Cinema(
                id = 91,
                name = "Ariston dei Fabbri",
                latitude = 45.6462406,
                longitude = 13.7655868,
                address = "Via dei Fabbri, 2a",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "http://www.aristontrieste.it/"
            ),
            Cinema(
                id = 92,
                name = "Don Zucchini",
                latitude = 44.7260677,
                longitude = 11.2888018,
                address = "Via Guercino, 19, Cento",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cinemadonzucchini.it/"
            ),
            Cinema(
                id = 93,
                name = "Nuovo Cinema Méliès",
                latitude = 43.111615,
                longitude = 12.3920275,
                address = "Via della Viola, 1, Perugia",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinegatti.it/sale/cinema-melies-en-plein-air/"
            ),
            Cinema(
                id = 94,
                name = "CineTeatro Comunale \"Gerardo Guerrieri\"",
                latitude = 40.6669321,
                longitude = 16.6060542,
                address = "Piazza Vittorio Veneto, 23",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.comingsoon.it/cinema/matera/cinema-comunale-guerrieri/2635/"
            ),
            Cinema(
                id = 95,
                name = "Cinema Multisala Le Giraffe",
                latitude = 45.5557442,
                longitude = 9.1634313,
                address = "Via Brasile, 4, Paderno Dugnano",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.legiraffe.it/il-multisala.html"
            ),
            Cinema(
                id = 96,
                name = "Cinema Fulgor",
                latitude = 44.0615607,
                longitude = 12.5658635,
                address = "Corso d'Augusto",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cinemafulgor.com/"
            ),
            Cinema(
                id = 97,
                name = "Cinema Teatro Augusteo",
                latitude = 40.6786748,
                longitude = 14.7557799,
                address = "Piazza Giovanni Amendola, 3, Salerno",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "http://www.cinemaaugusteo.it/"
            ),
            Cinema(
                id = 98,
                name = "Multisala Cinecity Mantova",
                latitude = 45.1686381,
                longitude = 10.8253162,
                address = "Piazzale Cesare Beccaria, 5, Mantova",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cineview.it/index.php?id_menu=79"
            ),
            Cinema(
                id = 99,
                name = "Cinema Apollo",
                latitude = 40.6816701,
                longitude = 14.7654468,
                address = "Via Michele Vernieri, Salerno",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "http://www.apollosalerno.it/"
            ),
            Cinema(
                id = 100,
                name = "Cinema Teatro San Demetrio",
                latitude = 40.6781388,
                longitude = 14.7716645,
                address = "Via Dalmazia, 4, Salerno",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cineteatrosandemetrio.it/"
            ),
            Cinema(
                id = 101,
                name = "Giunti Odeon",
                latitude = 43.7709968,
                longitude = 11.2525624,
                address = "Via degli Anselmi, 3 R;5 R;7 R",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.giuntiodeon.com"
            ),
            Cinema(
                id = 102,
                name = "The Space Cinemas",
                latitude = 45.4393596,
                longitude = 10.8643998,
                address = "Via Trentino, 1, Lugagnano di Sona",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "http://www.thespacecinema.it/portal/default/cinema/verona#!tab=programmazione"
            ),
            Cinema(
                id = 103,
                name = "Cinema Astra",
                latitude = 43.840708,
                longitude = 10.5034346,
                address = "Piazza del Giglio, 7",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.luccacinema.it/cinema-astra/"
            ),
            Cinema(
                id = 104,
                name = "Pop Up Cinema Jolly 4K",
                latitude = 44.4970588,
                longitude = 11.3370501,
                address = "Via Guglielmo Marconi, 14",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://jolly.popupcinema.18tickets.it/"
            ),
            Cinema(
                id = 105,
                name = "Cinema Bellinzona d'Essai",
                latitude = 44.489365,
                longitude = 11.3254947,
                address = "Via Bellinzona, 6, Bologna",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinemateatrobellinzona.it/"
            ),
            Cinema(
                id = 106,
                name = "Chaplin",
                latitude = 44.4907846,
                longitude = 11.3311906,
                address = "Piazza di Porta Saragozza, 5a, Bologna",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.capitolmultisala.com/le-sale/chaplin/"
            ),
            Cinema(
                id = 107,
                name = "Cinema Perla",
                latitude = 44.4997077,
                longitude = 11.3582776,
                address = "Via San Donato, 34/2, Bologna",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemaperlabologna.org/"
            ),
            Cinema(
                id = 108,
                name = "Starplex",
                latitude = 45.5325545,
                longitude = 9.7585396,
                address = "Strada Statale Soncinese, 498",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "http://www.romanodilombardia.starplex.it/romanodilombardia/"
            ),
            Cinema(
                id = 109,
                name = "Cinema ROMA d’essai",
                latitude = 43.9313129,
                longitude = 10.9214337,
                address = "Via Laudesi, 6",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "http://www.cinemaromapistoia.it"
            ),
            Cinema(
                id = 110,
                name = "Classico",
                latitude = 45.0661442,
                longitude = 7.6944342,
                address = "Piazza Vittorio Veneto, 5",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.circuitograntorino.com/"
            ),
            Cinema(
                id = 111,
                name = "Anteo Spazio Cinema",
                latitude = 45.4800568,
                longitude = 9.1877898,
                address = "Piazza Venticinque Aprile, 8, Milano",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.spaziocinema.info/"
            ),
            Cinema(
                id = 112,
                name = "Lanteri",
                latitude = 43.708706,
                longitude = 10.4143763,
                address = "Via San Michele degli Scalzi, 46",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cinemalanteri.com/"
            ),
            Cinema(
                id = 113,
                name = "Cinema Teatro Politeama",
                latitude = 45.0512865,
                longitude = 12.0575065,
                address = "Corso Vittorio Emanuele II, 211/a, Adria",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "http://www.politeama-adria.it/"
            ),
            Cinema(
                id = 114,
                name = "Rossini",
                latitude = 45.4352373,
                longitude = 12.3331236,
                address = "Calle del Teatro, 3397a, Venezia",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.mymovies.it/cinema/venezia/21454/"
            ),
            Cinema(
                id = 115,
                name = "PostModernissimo",
                latitude = 43.112821,
                longitude = 12.3933117,
                address = "Via del Carmine, 4, Perugia",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://postmodernissimo.com"
            ),
            Cinema(
                id = 116,
                name = "Ariston",
                latitude = 41.2151293,
                longitude = 13.5700004,
                address = "Piazza della Libertà, 19, Gaeta",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "http://www.aristongaeta.it"
            ),
            Cinema(
                id = 117,
                name = "Cinema Colombo",
                latitude = 42.5673713,
                longitude = 11.8176412,
                address = "Via Monte Grappa, 16, Valentano",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.facebook.com/CinemaColomboValentanoVTt"
            ),
            Cinema(
                id = 118,
                name = "Cinema Arsenale",
                latitude = 43.7139983,
                longitude = 10.402714,
                address = "Vicolo Scaramucci, 2",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.arsenalecinema.it/"
            ),
            Cinema(
                id = 119,
                name = "Cine Teatro Antidoto",
                latitude = 37.079898,
                longitude = 14.2095104,
                address = "Via Pandino, 2",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "http://cineteatroantidoto.it/"
            ),
            Cinema(
                id = 120,
                name = "Smeraldo",
                latitude = 43.9208667,
                longitude = 8.0997629,
                address = "Via Aurelia, 106, San Bartolomeo al Mare",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "http://www.cineinfo.it/smer.html"
            ),
            Cinema(
                id = 121,
                name = "Cineteatro Lux",
                latitude = 38.1396001,
                longitude = 13.3442944,
                address = "Via Francesco Paolo di Blasi, 25, Palermo",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://luxcineteatro.wixsite.com/luxcineteatro"
            ),
            Cinema(
                id = 122,
                name = "Cinema Di Francesca",
                latitude = 38.0380447,
                longitude = 14.0218366,
                address = "Corso Ruggero, 65, Cefalù",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinemadifrancesca.it/"
            ),
            Cinema(
                id = 123,
                name = "Cinema Il Portico",
                latitude = 43.7746183,
                longitude = 11.2781622,
                address = "Via Capo di Mondo, 66/68",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemailportico.it"
            ),
            Cinema(
                id = 124,
                name = "Cinema Teatro Sant'Angelo",
                latitude = 45.6772333,
                longitude = 9.1181303,
                address = "Via Giuseppe Garibaldi, 47, Lentate sul Seveso",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cineteatrolentate.it/"
            ),
            Cinema(
                id = 125,
                name = "Cinema Palma",
                latitude = 42.1547321,
                longitude = 12.2489705,
                address = "Via Giuseppe Garibaldi, 101, Trevignano Romano",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinemapalma.com/"
            ),
            Cinema(
                id = 126,
                name = "The Space Cinema Napoli",
                latitude = 40.8221019,
                longitude = 14.1772682,
                address = "Viale Giochi del Mediterraneo, 46, Napoli",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.thespacecinema.it/cinema/napoli/al-cinema"
            ),
            Cinema(
                id = 127,
                name = "Cinema Odissea",
                latitude = 39.2196446,
                longitude = 9.1042767,
                address = "Viale Trieste, 84, Cagliari",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemaodissea.it/"
            ),
            Cinema(
                id = 128,
                name = "Cinema Alfellini",
                latitude = 41.7868901,
                longitude = 12.6688795,
                address = "Viale Primo Maggio, 82, Grottaferrata",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "http://www.cinemaalfellini.it/alfellini/index.php"
            ),
            Cinema(
                id = 129,
                name = "Antoniano",
                latitude = 44.4863554,
                longitude = 11.3593961,
                address = "Via Guido Guinizelli, 3, Bologna",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://antonianobologna.it/categoria-prodotto/cinema-teatro/"
            ),
            Cinema(
                id = 130,
                name = "Cinema Impero",
                latitude = 44.6966087,
                longitude = 7.853653,
                address = "Via Vittorio Emanuele II, 211, Bra",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://cinemaimperobra.com/"
            ),
            Cinema(
                id = 131,
                name = "Rialto Studio",
                latitude = 44.4884504,
                longitude = 11.3491087,
                address = "Via Rialto, 19, Bologna",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.circuitocinemabologna.it/sale/"
            ),
            Cinema(
                id = 132,
                name = "Lumiere",
                latitude = 43.716951,
                longitude = 10.4004446,
                address = "Vicolo del Tidi, 2, Pisa",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.lumierepisa.com/"
            ),
            Cinema(
                id = 133,
                name = "Cinema Teatro Italia",
                latitude = 41.7859328,
                longitude = 14.1052727,
                address = "Castel di Sangro",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "http://www.cineteatroitalia.it"
            ),
            Cinema(
                id = 134,
                name = "Cinema Galliera",
                latitude = 44.5077541,
                longitude = 11.3467798,
                address = "Via Giacomo Matteotti, 27, Bologna",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinemateatrogalliera.it/"
            ),
            Cinema(
                id = 135,
                name = "Cineteatro Manzoni",
                latitude = 45.6961455,
                longitude = 9.4160744,
                address = "Via Papa Giovanni Ventitreesimo, 23, Merate",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "http://www.meratecinema.brianzaest.it"
            ),
            Cinema(
                id = 136,
                name = "Cinema Teatro La Compagnia",
                latitude = 43.7758278,
                longitude = 11.2568716,
                address = "Via Cavour, 50r, Firenze",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cinemalacompagnia.it/"
            ),
            Cinema(
                id = 137,
                name = "Sala Truffaut",
                latitude = 44.6456653,
                longitude = 10.9215313,
                address = "Via degli Adelardi, 4, Modena",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.circuitocinema.mo.it/sala-truffaut"
            ),
            Cinema(
                id = 138,
                name = "Multisala Iris",
                latitude = 38.2569,
                longitude = 15.6058721,
                address = "Via Consolare Pompea, 240",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.multisalairis.it/"
            ),
            Cinema(
                id = 139,
                name = "Cinecentrum Cittadella",
                latitude = 45.6637072,
                longitude = 11.8064672,
                address = "Viale dell'Artigianato, 4, Cittadella",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinecentrum.it/cittadella/"
            ),
            Cinema(
                id = 140,
                name = "Teatro Cinema Giardino",
                latitude = 44.2230982,
                longitude = 11.7729077,
                address = "Via della Fossa, 16",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cinemagiardino.it/"
            ),
            Cinema(
                id = 141,
                name = "Palladium",
                latitude = 45.8602099,
                longitude = 9.3960123,
                address = "Via Fiumicella, 12, Lecco",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://cinemapalladium.com/"
            ),
            Cinema(
                id = 142,
                name = "Multisala Abbondanza",
                latitude = 44.1181933,
                longitude = 12.3388188,
                address = "Corso Mazzini, 51, Gambettola",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.multisalaabbondanza.it/home/index.html"
            ),
            Cinema(
                id = 143,
                name = "Cinema Teatro Orfeo",
                latitude = 40.4734661,
                longitude = 17.2410203,
                address = "Via Pitagora, 78, Taranto",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.teatrorfeo.it/"
            ),
            Cinema(
                id = 144,
                name = "Multisala Politeama Clarici",
                latitude = 42.9578498,
                longitude = 12.7078009,
                address = "Via Giuseppe Garibaldi, 137, Foligno",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cinemaclarici.it"
            ),
            Cinema(
                id = 145,
                name = "Cinema Palestrina",
                latitude = 45.4850056,
                longitude = 9.2138936,
                address = "Via Giovanni Pierluigi da Palestrina, 7, Milano",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.progettolumiere.it/palestrina/home.php"
            ),
            Cinema(
                id = 146,
                name = "Cinema Teatro Italia",
                latitude = 44.6999733,
                longitude = 11.4044077,
                address = "Via Venti Settembre, San Pietro in Casale",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "http://cinemateatroitalia.altervista.org/"
            ),
            Cinema(
                id = 147,
                name = "Anteo CityLife",
                latitude = 45.4782164,
                longitude = 9.1547246,
                address = "Piazza Tre Torri, 1, Milano",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.spaziocinema.info/"
            ),
            Cinema(
                id = 148,
                name = "Roma",
                latitude = 44.4901842,
                longitude = 11.3557984,
                address = "Via Fondazza, 4, Bologna",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.circuitocinemabologna.it/sale/"
            ),
            Cinema(
                id = 149,
                name = "Cinema Rouge et noir",
                latitude = 38.1198118,
                longitude = 13.3581258,
                address = "Piazza Giuseppe Verdi, 8, Palermo",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.rougeetnoirpalermo.it/"
            ),
            Cinema(
                id = 150,
                name = "Cinelandia",
                latitude = 45.7212471,
                longitude = 9.2151866,
                address = "Strada provinciale 41 Vallassina, Arosio",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinelandia.it/"
            ),
            Cinema(
                id = 151,
                name = "Multisala Virgilio",
                latitude = 42.1006299,
                longitude = 12.1760164,
                address = "Via Salvatore Negretti, 44, Bracciano",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.fronteracinemas.com/#virgilio"
            ),
            Cinema(
                id = 152,
                name = "Multisala La Fenice",
                latitude = 40.7418368,
                longitude = 14.6126716,
                address = "Via Guglielmo Marconi, 65, Pagani",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.multisalalafenice.it/web/"
            ),
            Cinema(
                id = 153,
                name = "Campana",
                latitude = 45.6906586,
                longitude = 11.433256,
                address = "Via Vittorio Veneto, 2, Marano Vicentino",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "http://www.cinemacampana.org/"
            ),
            Cinema(
                id = 154,
                name = "Movieplex",
                latitude = 42.3665293,
                longitude = 13.372537,
                address = "Via Leonardo da Vinci, 7, L'Aquila",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.movieplex.it"
            ),
            Cinema(
                id = 155,
                name = "Cineteca - Sala Cervi",
                latitude = 44.4989591,
                longitude = 11.3371396,
                address = "Via Riva di Reno, 72, Bologna",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://cinetecadibologna.it/luogo/cinema-cervi/"
            ),
            Cinema(
                id = 156,
                name = "Cinema Araldo",
                latitude = 45.4543702,
                longitude = 8.6204178,
                address = "Via Maestra, 12, Novara",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.novaracinema.it/cinema/araldo"
            ),
            Cinema(
                id = 157,
                name = "Bookshop Cineteca di Bologna",
                latitude = 44.4942398,
                longitude = 11.3432075,
                address = "Voltone del Podestà",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://cinetecadibologna.it/luogo/bookshop-della-cineteca-di-bologna/"
            ),
            Cinema(
                id = 158,
                name = "Cinema Tiziano",
                latitude = 41.929071,
                longitude = 12.4679184,
                address = "Via Guido Reni, 2",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.facebook.com/Cinema-Tiziano-sito-ufficiale-159721814065333/"
            ),
            Cinema(
                id = 159,
                name = "Cinema Troisi",
                latitude = 41.8849464,
                longitude = 12.4716375,
                address = "Via Girolamo Induno, 1, Roma",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://cinematroisi.it/"
            ),
            Cinema(
                id = 160,
                name = "Cinema Teatro Cesare Caporali",
                latitude = 43.1267154,
                longitude = 12.0526383,
                address = "Piazzetta San Domenico, 1, Castiglione del Lago",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cinemacaporali.it/"
            ),
            Cinema(
                id = 161,
                name = "Cine+",
                latitude = 44.7697849,
                longitude = 10.7672611,
                address = "Piazzale Riccardo Finzi, 3",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinepiucorreggio.it/"
            ),
            Cinema(
                id = 162,
                name = "Cinema Parrocchiale di Mattarello",
                latitude = 46.0065876,
                longitude = 11.1287945,
                address = "Via Guido Poli, 7, Mattarello",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://cinema.parrocchiamattarello.it/"
            ),
            Cinema(
                id = 163,
                name = "Cinema Teatro Borgonuovo",
                latitude = 45.0702967,
                longitude = 7.5051241,
                address = "Via Roma, 149c, Rivoli",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemaborgonuovo.it"
            ),
            Cinema(
                id = 164,
                name = "Cine Arlecchino",
                latitude = 38.1451717,
                longitude = 13.3467461,
                address = "Via Imperatore Federico, 12, Palermo",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "http://arlecchinoariston.it/"
            ),
            Cinema(
                id = 165,
                name = "Cinema Tyndaris",
                latitude = 38.1368355,
                longitude = 14.9662898,
                address = "Via Venti Settembre, 85, Patti",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinematyndaris.it/"
            ),
            Cinema(
                id = 166,
                name = "Multisala Fiamma",
                latitude = 44.38633,
                longitude = 7.5397605,
                address = "Via Antonio Bassignano, 34, Cuneo",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinelandia.it/le-nostre-sale/cinema-cuneo.html"
            ),
            Cinema(
                id = 167,
                name = "Cinema Teatro Pedagna",
                latitude = 44.3460389,
                longitude = 11.6824308,
                address = "Via Antonio Vivaldi, 70, Imola",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemapedagna.it/"
            ),
            Cinema(
                id = 168,
                name = "UCI Cinemas",
                latitude = 41.9714063,
                longitude = 12.541205,
                address = "Via Alberto Lionello, 201, Roma",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.ucicinemas.it/cinema/lazio/roma/uci-cinemas-porta-di-roma-roma/"
            ),
            Cinema(
                id = 169,
                name = "Cinema-Teatro Contardo Ferrini",
                latitude = 44.4180128,
                longitude = 7.4271631,
                address = "Via Contardo Ferrini, 2, Caraglio",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://ferrinicult.wordpress.com/"
            ),
            Cinema(
                id = 170,
                name = "Sala comunale Fronte del Porto",
                latitude = 45.3830804,
                longitude = 11.8656033,
                address = "Via Santa Maria Assunta, 20, Padova",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.padovanet.it/sala-fronte-del-porto"
            ),
            Cinema(
                id = 171,
                name = "Cinema Aurora",
                latitude = 44.650138,
                longitude = 7.6552178,
                address = "Via Ghione, 10, Savigliano",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "http://www.cinemaaurorasavigliano.it/"
            ),
            Cinema(
                id = 172,
                name = "Cineteatro Circus",
                latitude = 42.4667691,
                longitude = 14.2087684,
                address = "Via Lanciano, 9, Pescara",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.pescaracityplex.it/"
            ),
            Cinema(
                id = 173,
                name = "Cineteatro Sant'Andrea",
                latitude = 42.4665959,
                longitude = 14.2154065,
                address = "Piazza Sant'Andrea, 7, Pescara",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.pescaracityplex.it/"
            ),
            Cinema(
                id = 174,
                name = "Cityplex Tiffany",
                latitude = 38.1416457,
                longitude = 13.3408677,
                address = "Via Giorgio Boris Giuliano, 38, Palermo",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://cityplexpalermo.it/"
            ),
            Cinema(
                id = 175,
                name = "Cityplex Metropolitan",
                latitude = 38.1570312,
                longitude = 13.325654,
                address = "Viale Strasburgo, 358, Palermo",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://cityplexpalermo.it/"
            ),
            Cinema(
                id = 176,
                name = "Multiplex Astra",
                latitude = 42.001617,
                longitude = 13.4325157,
                address = "Via C. Cavour, 62, Avezzano",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.multicinema.it/programmazione/astra"
            ),
            Cinema(
                id = 177,
                name = "Cineteatro Colosseum",
                latitude = 38.0819767,
                longitude = 13.358383,
                address = "Via Rossa Guido, 7, Palermo",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://cineteatrocolosseum.wixsite.com/colosseum"
            ),
            Cinema(
                id = 178,
                name = "UCI Cinemas Palermo",
                latitude = 38.0902824,
                longitude = 13.4125087,
                address = "Via Filipo Pecoraino, Palermo",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.ucicinemas.it/cinema/sicilia/palermo/uci-cinemas-palermo/"
            ),
            Cinema(
                id = 179,
                name = "Cinema Multisala Aurora",
                latitude = 38.1870432,
                longitude = 13.2897921,
                address = "Via Tommaso Natale, 177, Palermo",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.auroramultisalapalermo.it/"
            ),
            Cinema(
                id = 180,
                name = "CiakCity",
                latitude = 42.2664211,
                longitude = 14.4338977,
                address = "Rocca San Giovanni",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.ciakcity.it/"
            ),
            Cinema(
                id = 181,
                name = "Cinema Smeraldo",
                latitude = 42.6612179,
                longitude = 13.710686,
                address = "Via Maestri del Lavoro, 4, Teramo",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.smeraldocinema.it//"
            ),
            Cinema(
                id = 182,
                name = "CiakCity",
                latitude = 42.1922855,
                longitude = 14.2218304,
                address = "Largo Pignatari, Guardiagrele",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.ciakcity.it/"
            ),
            Cinema(
                id = 183,
                name = "UCI Cinemas Orio",
                latitude = 45.661523,
                longitude = 9.6966183,
                address = "Via Toscana, 2, Azzano San Paolo",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.ucicinemas.it/cinema/lombardia/bergamo/uci-cinemas-orio-bergamo/"
            ),
            Cinema(
                id = 184,
                name = "Cinema Sala degli Artisti",
                latitude = 43.1622125,
                longitude = 13.7196002,
                address = "Via Goffredo Mameli, 2, Fermo",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://cinema27.wixsite.com/saladegliartisti/"
            ),
            Cinema(
                id = 185,
                name = "L'AltroCinema",
                latitude = 42.4585418,
                longitude = 14.2137546,
                address = "Via Gabriele D'Annunzio, 102, Pescara",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.pescaraporno.it/"
            ),
            Cinema(
                id = 186,
                name = "Cineteatro San Gaetano",
                latitude = 36.7660219,
                longitude = 11.9725751,
                address = "Via Padre Piccirilli, 30, Scauri (Pantelleria)",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinemapantelleria.it/"
            ),
            Cinema(
                id = 187,
                name = "Igioland Multisala",
                latitude = 42.1091357,
                longitude = 13.845882,
                address = "Strada Provinciale Corfiniense, Corfinio",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.igioland.com/"
            ),
            Cinema(
                id = 188,
                name = "Cinema Pacifico",
                latitude = 42.0488244,
                longitude = 13.9253778,
                address = "Via Roma, 27, Sulmona",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://pacifico.corfinio.it/"
            ),
            Cinema(
                id = 189,
                name = "Cinema Roma",
                latitude = 41.8492461,
                longitude = 14.0791919,
                address = "Viale Roma, 64, Roccaraso",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinemaroccaraso.it/"
            ),
            Cinema(
                id = 190,
                name = "Multiplex Universo",
                latitude = 42.5726685,
                longitude = 14.0969256,
                address = "Via Nazionale Nord, Silvi",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://multicinema.it/Programmazione?cinema=universo"
            ),
            Cinema(
                id = 191,
                name = "Cineplex Arcobaleno",
                latitude = 42.8374983,
                longitude = 13.8986883,
                address = "Contrada Vibrata, Colonnella",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://cineplexarcobaleno.it/"
            ),
            Cinema(
                id = 192,
                name = "Multiplex Le Befane",
                latitude = 44.0406392,
                longitude = 12.5861528,
                address = "Via Caduti di Nassiriya, 20",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.giomettirealestatecinema.it/cinema"
            ),
            Cinema(
                id = 193,
                name = "Cinema Odeon",
                latitude = 43.3196775,
                longitude = 11.3310246,
                address = "Galleria Odeon, 31, Siena",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.sienacinema.it/cinema-odeon/"
            ),
            Cinema(
                id = 194,
                name = "UCI Cinemas Megalò",
                latitude = 42.3436104,
                longitude = 14.1287767,
                address = "Via Tirino, Chieti",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.ucicinemas.it/cinema/abruzzo/chieti/uci-cinemas-megalo/"
            ),
            Cinema(
                id = 195,
                name = "Cinema Stella",
                latitude = 42.7663655,
                longitude = 11.1078126,
                address = "Via Mameli, 24, Grosseto",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemastella.com"
            ),
            Cinema(
                id = 196,
                name = "Cinema nel Chiostro",
                latitude = 42.7612834,
                longitude = 11.1122423,
                address = "Via Mazzini, 36, Grosseto",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://cineforum.marte.18tickets.it/"
            ),
            Cinema(
                id = 197,
                name = "Supercinema",
                latitude = 42.4372881,
                longitude = 11.2092957,
                address = "Corso Italia, 129, Orbetello",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://orbetellocinema.com"
            ),
            Cinema(
                id = 198,
                name = "Arena Verdi",
                latitude = 43.0926056,
                longitude = 10.5471052,
                address = "Via Sandro Pertini, San Vincenzo",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "http://www.cinemateatroverdi.altervista.org/"
            ),
            Cinema(
                id = 199,
                name = "Cinema Teatro Solvay",
                latitude = 43.3905302,
                longitude = 10.44144,
                address = "Via Ernesto Solvay, 20, Rosignano Solvay",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.decima-musa.eu/"
            ),
            Cinema(
                id = 200,
                name = "Cinema Dro",
                latitude = 45.9625117,
                longitude = 10.9098153,
                address = "Via Cesare Battisti, 7, Dro",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cinemadro.com/"
            ),
            Cinema(
                id = 201,
                name = "Cineteatro Buccomino",
                latitude = 40.9658633,
                longitude = 16.0944311,
                address = "Corso Umberto I, Spinazzola",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://buccominobistrot.it/cinema/"
            ),
            Cinema(
                id = 202,
                name = "Il Cinemino",
                latitude = 45.4541227,
                longitude = 9.2083301,
                address = "Via Seneca, 6, Milano",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.ilcinemino.it/"
            ),
            Cinema(
                id = 203,
                name = "Cineteatro Oriana Fallaci",
                latitude = 40.5884221,
                longitude = 9.0042916,
                address = "Largo Oriana Fallaci, Ozieri",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://cineteatro-orianna-fallaci.business.site/"
            ),
            Cinema(
                id = 204,
                name = "Cinema Mexico",
                latitude = 45.4528535,
                longitude = 9.160193,
                address = "Via Savona, 57, Milano",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.angelos.it/"
            ),
            Cinema(
                id = 205,
                name = "Cinema Beltrade",
                latitude = 45.4924673,
                longitude = 9.214887,
                address = "Via Nino Oxilia, 10, Milano",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinemabeltrade.net/"
            ),
            Cinema(
                id = 206,
                name = "Multisala Paolillo",
                latitude = 41.3179981,
                longitude = 16.2796968,
                address = "Corso Giuseppe Garibaldi, 25, 29, Barletta",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://cinemapaolillo.it"
            ),
            Cinema(
                id = 207,
                name = "Cinema Turroni",
                latitude = 45.7794981,
                longitude = 12.4916574,
                address = "Via Giuseppe Garibaldi, 43, Oderzo",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.enricopizzuti.it/circolo-cinema-oderzo/"
            ),
            Cinema(
                id = 208,
                name = "Multisala Oxer",
                latitude = 41.4590296,
                longitude = 12.8889597,
                address = "Viale Pier Luigi Nervi, 124, Latina",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://multisalaoxer.18tickets.it/"
            ),
            Cinema(
                id = 209,
                name = "Sala BCC Città e Cultura",
                latitude = 44.3531439,
                longitude = 11.7151744,
                address = "Via Emilia, 210a, Imola",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinemaincentro.com/Cinema-Centrale-Imola"
            ),
            Cinema(
                id = 210,
                name = "Cinema Multilanghe",
                latitude = 44.5301484,
                longitude = 7.9463567,
                address = "Piazza Gorizia, 9, Dogliani",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.multilanghe.it/"
            ),
            Cinema(
                id = 211,
                name = "Eden",
                latitude = 44.7864127,
                longitude = 10.8844642,
                address = "Via Santa Chiara, 22",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemaedencarpi.it/"
            ),
            Cinema(
                id = 212,
                name = "Filmclub Meran",
                latitude = 46.6716405,
                longitude = 11.1587765,
                address = "Rennweg - Via delle Corse, 25",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.filmclub.it"
            ),
            Cinema(
                id = 213,
                name = "Cinema Modernissimo",
                latitude = 44.4945212,
                longitude = 11.3439516,
                address = "Piazza Re Enzo, 1, Bologna",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://cinetecadibologna.it/luogo/cinema-modernissimo/"
            ),
            Cinema(
                id = 214,
                name = "Cinema Eden",
                latitude = 44.6258819,
                longitude = 10.5632166,
                address = "Via Armando Teneggi, 1, Quattro Castella",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinemaeden.org/"
            ),
            Cinema(
                id = 215,
                name = "Multicinema Galleria",
                latitude = 41.118706,
                longitude = 16.8683224,
                address = "Corso Italia, 17, Bari",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.multicinemagalleria.it/"
            ),
            Cinema(
                id = 216,
                name = "Cinema Multiplex Giometti",
                latitude = 43.518788,
                longitude = 13.2540176,
                address = "Via Marco Polo, 5, Jesi",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.giomettirealestatecinema.it/cinema/multiplex-jesi/programmazione"
            ),
            Cinema(
                id = 217,
                name = "Cinema Teatro Castellano",
                latitude = 45.7783916,
                longitude = 8.7949225,
                address = "Via Acquadro, 32",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinemacastellani.it"
            ),
            Cinema(
                id = 218,
                name = "Cinema Alessandro VII",
                latitude = 43.3219336,
                longitude = 11.3313609,
                address = "5, Siena",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.sienacinema.it/cinema-alessandro-vii/"
            ),
            Cinema(
                id = 219,
                name = "Cinema Teatro Tivoli",
                latitude = 44.4938047,
                longitude = 11.3838647,
                address = "Via Giuseppe Massarenti, 418, Bologna",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemativoli.it/"
            ),
            Cinema(
                id = 220,
                name = "CINEMA ITALIA",
                latitude = 43.4710518,
                longitude = 11.285881,
                address = "Via Trento e Trieste, 32, Castellina in Chianti",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.mymovies.it/"
            ),
            Cinema(
                id = 221,
                name = "Il Piccolo",
                latitude = 41.1629075,
                longitude = 16.7489786,
                address = "Via Giannone de Maioribus, 4, Santo Spirito",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinemapiccolo.it/"
            ),
            Cinema(
                id = 222,
                name = "Lo Schermo Bianco",
                latitude = 45.6937094,
                longitude = 9.7039064,
                address = "Piazza Fabrizio De Andrè, Bergamo",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://lab80.it/it/programmazione?placeFilter[2]=91"
            ),
            Cinema(
                id = 223,
                name = "Cinema Teatro Petrarca Multisala",
                latitude = 45.1385287,
                longitude = 7.7759143,
                address = "Via Francesco Petrarca, 7",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.multisalapetrarca.it/"
            ),
            Cinema(
                id = 224,
                name = "Medicinema Niguarda",
                latitude = 45.5111401,
                longitude = 9.1878858,
                address = "Piazza Ospedale Maggiore, 3, Milano",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.medicinema-italia.org/"
            ),
            Cinema(
                id = 225,
                name = "Sala Filmstudio 90",
                latitude = 45.8216106,
                longitude = 8.8312717,
                address = "Via Carlo de Cristoforis, 5, Varese",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.filmstudio90.it/sala-filmstudio90/"
            ),
            Cinema(
                id = 226,
                name = "Casablanca Multicinema",
                latitude = 40.4632059,
                longitude = 17.3641971,
                address = "SS7, San Giorgio Ionico",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://casablancamulticine.com/"
            ),
            Cinema(
                id = 227,
                name = "Masetti Cinema",
                latitude = 43.8380562,
                longitude = 13.0158212,
                address = "Via Don Giovanni Bosco, 12, Fano",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.masetticinema.it/"
            ),
            Cinema(
                id = 228,
                name = "Premiato Cinema multisala SOLARIS - Pesaro",
                latitude = 43.8984476,
                longitude = 12.9037068,
                address = "Via Filippo Turati, 42",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://solarispesaro.it/"
            ),
            Cinema(
                id = 229,
                name = "Cinema Moretto",
                latitude = 45.5354445,
                longitude = 10.2232797,
                address = "Via Moretto, angolo Piazza Sant'Alessandro",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.ilregnodelcinema.com/moretto/"
            ),
            Cinema(
                id = 230,
                name = "Cinema Gabbiano",
                latitude = 43.7128504,
                longitude = 13.2150797,
                address = "Via Angelo Maierini, 2",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinemagabbiano.it/"
            ),
            Cinema(
                id = 231,
                name = "Cinema Vittoria",
                latitude = 40.9731551,
                longitude = 14.2095824,
                address = "Piazza Vittorio Emanuele III, 38-39, Aversa",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemavittoriaaversa.it/"
            ),
            Cinema(
                id = 232,
                name = "Cineteatro Massimo",
                latitude = 42.4637776,
                longitude = 14.2128492,
                address = "Via Caduta del Forte, 15, Pescara",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.pescaracityplex.it/"
            ),
            Cinema(
                id = 233,
                name = "CineTempio Teatro Giordo",
                latitude = 40.900343,
                longitude = 9.1041733,
                address = "Via Asilo, 2, Tempio Pausania",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinetempio.it"
            ),
            Cinema(
                id = 234,
                name = "UCI Cinemas Pioltello",
                latitude = 45.5031911,
                longitude = 9.3218062,
                address = "Via San Francesco, 33, Pioltello",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.ucicinemas.it/cinema/lombardia/milano/uci-cinemas-pioltello-milano/"
            ),
            Cinema(
                id = 235,
                name = "'U Cinemittu",
                latitude = 42.2731624,
                longitude = 12.9659429,
                address = "Via Borgo, 3, Longone Sabino",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.ucinemittu.it/"
            ),
            Cinema(
                id = 236,
                name = "Giometti Cinema",
                latitude = 43.2887668,
                longitude = 13.7366796,
                address = "Via Fratte, 41, Porto Sant'Elpidio",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.giomettirealestatecinema.it/"
            ),
            Cinema(
                id = 237,
                name = "CiakCity Lanciano",
                latitude = 42.2271878,
                longitude = 14.4068694,
                address = "Via Vincenzo Bellisario, 41, Lanciano",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.ciakcity.it/"
            ),
            Cinema(
                id = 238,
                name = "The Space Cinema",
                latitude = 41.8151681,
                longitude = 12.4113329,
                address = "Viale Salvatore Rebecchini, 3, Roma",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.thespacecinema.it/i-nostri-cinema/roma-parco-de-medici/info-cinema"
            ),
            Cinema(
                id = 239,
                name = "Movie Planet",
                latitude = 45.338478,
                longitude = 8.4518157,
                address = "SP11bis, Borgo Vercelli",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.movieplanetgroup.it/mp_vercelli/index.php"
            ),
            Cinema(
                id = 240,
                name = "Cinelandia",
                latitude = 45.1294995,
                longitude = 8.4556365,
                address = "Piazza d'Armi, 1, Casale Monferrato",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cinelandia.it/le-nostre-sale/cinema-casale.html"
            ),
            Cinema(
                id = 241,
                name = "Cinema Gloria",
                latitude = 45.7914443,
                longitude = 9.0775068,
                address = "Via Varesina, 72, Como",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.spaziogloria.com/"
            ),
            Cinema(
                id = 242,
                name = "Cinema Senio",
                latitude = 44.2238739,
                longitude = 11.6235776,
                address = "Via Roma, 46, Casola Valsenio",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "http://cinemasenio.blogspot.it/"
            ),
            Cinema(
                id = 243,
                name = "Cinema Teatro Italia",
                latitude = 45.5754158,
                longitude = 9.0765464,
                address = "Via Varese, 25a, Garbagnate Milanese",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cineteatrogarbagnate.it/"
            ),
            Cinema(
                id = 244,
                name = "Auditorium San Luigi",
                latitude = 45.5745266,
                longitude = 9.0777952,
                address = "Via Padre Eliseo Vismara, 2, Garbagnate Milanese",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cineteatrogarbagnate.it/"
            ),
            Cinema(
                id = 245,
                name = "Cinema Teatro Valpantena",
                latitude = 45.5182877,
                longitude = 11.0176687,
                address = "Piazza Carlo Ederle, Grezzana",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinemateatrovalpantena.it/"
            ),
            Cinema(
                id = 246,
                name = "Cinema Europa",
                latitude = 44.4961301,
                longitude = 11.330991,
                address = "Via Pietralata, 55/A, Bologna",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.circuitocinemabologna.it/sale/"
            ),
            Cinema(
                id = 247,
                name = "Arena Puccini",
                latitude = 44.5069255,
                longitude = 11.3517489,
                address = "Via Sebastiano Serlio, 25/2, Bologna",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://cinetecadibologna.it/luogo/arena-puccini/"
            ),
            Cinema(
                id = 248,
                name = "Cinema Nuovo Nosadella",
                latitude = 44.5025076,
                longitude = 11.3294289,
                address = "Via Lodovico Berti, 2/7, Bologna",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.nosadella.it/"
            ),
            Cinema(
                id = 249,
                name = "Cinema Fossolo",
                latitude = 44.4821084,
                longitude = 11.3878299,
                address = "Viale Abramo Lincoln, 3, Bologna",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://cinemafossolo.it/"
            ),
            Cinema(
                id = 250,
                name = "Cinedream",
                latitude = 44.311976,
                longitude = 11.8973762,
                address = "Via Granarolo, 55, Faenza",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "http://www.cinedream.it"
            ),
            Cinema(
                id = 251,
                name = "Cinema Politeama",
                latitude = 45.4656318,
                longitude = 7.8760139,
                address = "Via Piave, 3, Ivrea",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://politeamaivrea.wordpress.com/"
            ),
            Cinema(
                id = 252,
                name = "Cinema Multisala Isola Verde",
                latitude = 43.710327,
                longitude = 10.4339097,
                address = "Via Vittorio Frascani, Pisa",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "http://www.multisalaisolaverde.com/"
            ),
            Cinema(
                id = 253,
                name = "DUEL Village Cinema",
                latitude = 41.077854,
                longitude = 14.3536483,
                address = "Vicolo Pietro Mascagni, Caserta",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.facebook.com/191160194246682"
            ),
            Cinema(
                id = 254,
                name = "UCI Cinemas Showville",
                latitude = 41.0907937,
                longitude = 16.8877972,
                address = "Via Mario Giannini, 9, Bari",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.ucicinemas.it/cinema/puglia/bari/uci-cinemas-showville-bari/"
            ),
            Cinema(
                id = 255,
                name = "VIP",
                latitude = 45.4447916,
                longitude = 8.6105152,
                address = "Via Costantino Perazzi, 3c, Novara",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "http://www.novaracinema.it"
            ),
            Cinema(
                id = 256,
                name = "Filmclub - Capitol",
                latitude = 46.500448,
                longitude = 11.3560042,
                address = "Via Dr. Josef Streiter - Dr.-Josef-Streiter-Gasse, 8, Bolzano - Bozen",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.filmclub.it/it"
            ),
            Cinema(
                id = 257,
                name = "Cineplexx",
                latitude = 46.49299,
                longitude = 11.3609441,
                address = "Schlachthofstraße, 53a",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cineplexx.bz.it"
            ),
            Cinema(
                id = 258,
                name = "Cinema Belvedere",
                latitude = 44.5093638,
                longitude = 10.7315237,
                address = "Via Radici Nord, 6",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinemabelvedere.it/"
            ),
            Cinema(
                id = 259,
                name = "UCI Luxe",
                latitude = 43.8438473,
                longitude = 11.1373622,
                address = "Via Fratelli Cervi, 9, Campi Bisenzio",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.ucicinemas.it/cinema/toscana/firenze/uci-cinemas-campi-bisenzio-firenze/"
            ),
            Cinema(
                id = 260,
                name = "Kinemax Monfalcone",
                latitude = 45.7971577,
                longitude = 13.5238654,
                address = "Via Grado, 54",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.kinemax.it/"
            ),
            Cinema(
                id = 261,
                name = "Cineteatro Duse",
                latitude = 45.5745136,
                longitude = 9.3504604,
                address = "Via Marco d'Agrate, 49, Agrate Brianza",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.ctduse.it/"
            ),
            Cinema(
                id = 262,
                name = "Cinema Teatro Cristallo",
                latitude = 45.7806821,
                longitude = 12.491547,
                address = "Via Giuseppe Garibaldi, 44, Oderzo",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "http://www.cinemacristallo.com/wp/"
            ),
            Cinema(
                id = 263,
                name = "Cinema Corso",
                latitude = 45.0507364,
                longitude = 9.690857,
                address = "Corso Vittorio Emanuele Secondo, 81, Piacenza",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemacorsopc.com/"
            ),
            Cinema(
                id = 264,
                name = "Politeama Cityplex",
                latitude = 41.8080004,
                longitude = 12.6774935,
                address = "Largo Augusto Panizza, 5",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://politeama.it/"
            ),
            Cinema(
                id = 265,
                name = "Cinema Teatro Comunale",
                latitude = 43.7916834,
                longitude = 7.6072738,
                address = "Via Lodovico Aprosio, 24, Ventimiglia",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://aristonsanremo.com/"
            ),
            Cinema(
                id = 266,
                name = "Cinema Barberini",
                latitude = 41.9034035,
                longitude = 12.4892397,
                address = "P.za Barberini, 25",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinemabarberini.it/"
            ),
            Cinema(
                id = 267,
                name = "Multiplex Arcadia",
                latitude = 45.4988315,
                longitude = 9.419014,
                address = "Via Martiri della Libertà, 5, Melzo",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.arcadiacinema.com/melzo/prog.php"
            ),
            Cinema(
                id = 268,
                name = "Cinema Moderno",
                latitude = 44.1127143,
                longitude = 9.9574417,
                address = "Via del Carmine, 35, Sarzana",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.moderno.it/"
            ),
            Cinema(
                id = 269,
                name = "Cineteatro Victoria",
                latitude = 46.321742,
                longitude = 9.4070899,
                address = "Via Don Giovanni Battista Picchi, 2, Chiavenna",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cineteatrovictoria.com/"
            ),
            Cinema(
                id = 270,
                name = "Arena Borghesi",
                latitude = 44.2831156,
                longitude = 11.8760566,
                address = "Viale Stradone, Faenza",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cineclubilraggioverde.it/"
            ),
            Cinema(
                id = 271,
                name = "Cinema Fraiteve",
                latitude = 44.958366,
                longitude = 6.8795582,
                address = "Piazzale Fraiteve, 5, Sestriere",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.srcinema.it/fraiteve/index.php"
            ),
            Cinema(
                id = 272,
                name = "Cineteatro \"La Provvidenza\"",
                latitude = 40.2275745,
                longitude = 15.2632377,
                address = "Via Valenzani, Vallo della Lucania",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "http://www.diocesivallodellalucania.it/provvidenza/"
            ),
            Cinema(
                id = 273,
                name = "Sala delle Provincie d'Essai",
                latitude = 41.9109306,
                longitude = 12.5214211,
                address = "Viale delle Provincie, 41",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinemadelleprovincie.it/"
            ),
            Cinema(
                id = 274,
                name = "Multisala Jolly",
                latitude = 41.9086808,
                longitude = 12.5227871,
                address = "Via Giano della Bella, 4, Roma",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://multisalajolly.it/"
            ),
            Cinema(
                id = 275,
                name = "Cinema Multisala Settebello",
                latitude = 44.060935,
                longitude = 12.5766048,
                address = "Via Roma, 70, Rimini",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemasettebello.it/"
            ),
            Cinema(
                id = 276,
                name = "Cinelandia Park",
                latitude = 45.6588942,
                longitude = 8.8097682,
                address = "Viale Lombardia, 51, Gallarate",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cinelandia.it/cinema-cinelandia-gallarate/"
            ),
            Cinema(
                id = 277,
                name = "Cinema Europa",
                latitude = 44.2821514,
                longitude = 11.8914219,
                address = "Via Sant'Antonino, 4, Faenza",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinemaeuropa.it/"
            ),
            Cinema(
                id = 278,
                name = "The Space Cinema - Salerno",
                latitude = 40.6478312,
                longitude = 14.8211237,
                address = "Viale Antonio Bandiera, Salerno",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.thespacecinema.it/portal/default/cinema/salerno"
            ),
            Cinema(
                id = 279,
                name = "UCI Cinemas Seven Gioia Del Colle",
                latitude = 40.809472,
                longitude = 16.9218837,
                address = "Via Federico Fellini, 80, Gioia del Colle",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.ucicinemas.it/cinema/puglia/bari/uci-cinemas-seven-gioia-del-colle/"
            ),
            Cinema(
                id = 280,
                name = "Cinema Marconi",
                latitude = 45.2969694,
                longitude = 12.0338217,
                address = "Via Gauslino, 7, Piove di Sacco",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cinemamarconi.com"
            ),
            Cinema(
                id = 281,
                name = "Cinema Mignon",
                latitude = 45.1521468,
                longitude = 10.7969649,
                address = "Via Gaetano Benzoni, 22, Mantova",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinemamignon.com/"
            ),
            Cinema(
                id = 282,
                name = "Cinema Teatro delle Arti",
                latitude = 45.6585769,
                longitude = 8.7922696,
                address = "Via Don Giovanni Minzoni, 5, Gallarate",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "http://www.teatrodellearti.it/"
            ),
            Cinema(
                id = 283,
                name = "UCI Cinemas",
                latitude = 45.4076838,
                longitude = 9.1513584,
                address = "Via Cascina Venina",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.ucicinemas.it/cinema/lombardia/milano/uci-cinemas-milanofiori-milano/"
            ),
            Cinema(
                id = 284,
                name = "Cinema Kennedy",
                latitude = 40.6531131,
                longitude = 16.6189583,
                address = "Via Cappuccini, 23, Matera",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "http://www.cinemamatera.it/kennedy/index.asp"
            ),
            Cinema(
                id = 285,
                name = "The Space Cinema",
                latitude = 42.7810866,
                longitude = 11.1136789,
                address = "Via Canada, 80, Grosseto",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.thespacecinema.it/i-nostri-cinema/grosseto/al-cinema"
            ),
            Cinema(
                id = 286,
                name = "Teatro Cinema San Rocco",
                latitude = 45.6502634,
                longitude = 9.210462,
                address = "Via Camillo Benso conte di Cavour, 83, Seregno",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "http://www.teatrosanrocco.com/"
            ),
            Cinema(
                id = 287,
                name = "The Space Cinema",
                latitude = 40.3766902,
                longitude = 18.1468708,
                address = "Via Rosanna Benzi, Surbo",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.thespacecinema.it/"
            ),
            Cinema(
                id = 288,
                name = "Cinema Massimo",
                latitude = 40.3514766,
                longitude = 18.1741683,
                address = "Viale Francesco Lo Re, 3, Lecce",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.multisalamassimo.it/"
            ),
            Cinema(
                id = 289,
                name = "Cinema Sofia",
                latitude = 40.8232022,
                longitude = 14.1258381,
                address = "Via Carlo Maria Rosini, 12, Pozzuoli",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "http://www.multisalasofia.com/"
            ),
            Cinema(
                id = 290,
                name = "Multisala Odeon",
                latitude = 43.718062,
                longitude = 10.4044558,
                address = "Piazza San Paolo all'Orto, 18, Pisa",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.multisalaodeon.com/"
            ),
            Cinema(
                id = 291,
                name = "Multisala Gaveli",
                latitude = 41.1035486,
                longitude = 14.8255997,
                address = "Contrada Piano Cappelle, Benevento",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.gaveli.it/"
            ),
            Cinema(
                id = 292,
                name = "Cinema Centrale",
                latitude = 43.8677033,
                longitude = 10.248567,
                address = "Via Cesare Battisti, 67, Viareggio",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "http://www.viareggiocinema.com/cinema-centrale/"
            ),
            Cinema(
                id = 293,
                name = "Cinema Silvio Pellico",
                latitude = 45.6284742,
                longitude = 9.0394599,
                address = "Via Silvio Pellico",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "http://www.pellicosaronno.it/"
            ),
            Cinema(
                id = 294,
                name = "Starplex",
                latitude = 41.9587021,
                longitude = 12.3996767,
                address = "Via della Lucchina, 90",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "romaottavia.starplex.it"
            ),
            Cinema(
                id = 295,
                name = "Cinema Centrale Arthouse",
                latitude = 45.0663348,
                longitude = 7.6851377,
                address = "Via Carlo Alberto, 27",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://cinemacentrale.wordpress.com/"
            ),
            Cinema(
                id = 296,
                name = "Cinema Rondinella",
                latitude = 45.5435433,
                longitude = 9.2305091,
                address = "Viale Giacomo Matteotti, 425, Sesto San Giovanni",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "http://www.cinemarondinella.it"
            ),
            Cinema(
                id = 297,
                name = "Cinema Metropolis",
                latitude = 45.7207637,
                longitude = 8.6002259,
                address = "Castelletto sopra Ticino",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.metropolisarea.it/"
            ),
            Cinema(
                id = 298,
                name = "Cinema Odeon",
                latitude = 45.5484837,
                longitude = 11.5483413,
                address = "Corso Andrea Palladio, 176, Vicenza",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.odeonline.it/"
            ),
            Cinema(
                id = 299,
                name = "Cinema Marconi",
                latitude = 45.2323995,
                longitude = 11.8722944,
                address = "Via Vittorio Emanuele II, 23, Conselve",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://valesalmin.wixsite.com/marconiconselve"
            ),
            Cinema(
                id = 300,
                name = "Torrevillage Multiplex",
                latitude = 41.1966813,
                longitude = 14.7373915,
                address = "Zona Industriale, Torrepalazzo, Torrecuso",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "http://torrevillage.it/"
            ),
            Cinema(
                id = 301,
                name = "Cinema Adriano",
                latitude = 43.7928237,
                longitude = 11.2467744,
                address = "Via Giandomenico Romagnosi, 46, Firenze",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinemaadriano.it/"
            ),
            Cinema(
                id = 302,
                name = "Stardust Village",
                latitude = 41.8189159,
                longitude = 12.4452654,
                address = "Via di Decima, 72",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://stardustvillage.it/"
            ),
            Cinema(
                id = 303,
                name = "Cinema Politeama",
                latitude = 40.7562016,
                longitude = 14.445906,
                address = "Corso Vittorio Emanuele III, 374, Torre Annunziata",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "http://www.cinematorreannunziata.it"
            ),
            Cinema(
                id = 304,
                name = "Cinema Teatro San Giuseppe",
                latitude = 45.5529128,
                longitude = 9.3031986,
                address = "Via Italia, 76, Brugherio",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.sangiuseppeonline.it/"
            ),
            Cinema(
                id = 305,
                name = "Cinema Arena \"La Pineta\"",
                latitude = 40.4319215,
                longitude = 16.8887976,
                address = "Viale Trieste, 116, Marina di Ginosa",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://cinemamarinadiginosa.blogspot.com/"
            ),
            Cinema(
                id = 306,
                name = "Multisala Moderno",
                latitude = 42.4028756,
                longitude = 12.8578109,
                address = "Via Cintia, 56, Rieti",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.multisalamoderno.com/"
            ),
            Cinema(
                id = 307,
                name = "Cinema Teatro Italia",
                latitude = 43.6056689,
                longitude = 13.5049467,
                address = "Corso Carlo Alberto di Savoia, 77, Ancona",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.teoremacinema.it/cinema-teatro-italia/"
            ),
            Cinema(
                id = 308,
                name = "Cinema Moderno",
                latitude = 43.3105101,
                longitude = 10.5160684,
                address = "Viale Italia, 4, Cecina",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://cinematirrenocecina.biz/"
            ),
            Cinema(
                id = 309,
                name = "Cinema Multisala Tirreno",
                latitude = 43.3114675,
                longitude = 10.5154165,
                address = "Via Bruno Buozzi, 9, Cecina",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://cinematirrenocecina.biz/"
            ),
            Cinema(
                id = 310,
                name = "Starplex Marano Vicentino",
                latitude = 45.6980404,
                longitude = 11.4124548,
                address = "Via Monte Pasubio, 130, Marano Vicentino",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "http://www.maranovicentino.starplex.it/maranovi/"
            ),
            Cinema(
                id = 311,
                name = "Cinema Teatro Arese",
                latitude = 45.5497021,
                longitude = 9.0777659,
                address = "Piazza Generale Carlo Alberto Dalla Chiesa, 4, Arese",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "http://www.cinemateatroarese.it/"
            ),
            Cinema(
                id = 312,
                name = "Ariston",
                latitude = 41.7236171,
                longitude = 13.0134473,
                address = "Via degli Atleti, 5, Colleferro",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://multisale-ariston.it/"
            ),
            Cinema(
                id = 313,
                name = "Cineteatro Rosso Di San Secondo",
                latitude = 38.156689,
                longitude = 14.7388357,
                address = "Lungomare Andrea Doria, 112a, Capo d'Orlando",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.cinemamultimedia.it/"
            ),
            Cinema(
                id = 314,
                name = "Cinema Teatro Filo",
                latitude = 45.135901,
                longitude = 10.0247864,
                address = "Piazza dei Filodrammatici, 4",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.cinemafilo.com/"
            ),
            Cinema(
                id = 315,
                name = "Cineteatro San Fedele",
                latitude = 45.6881109,
                longitude = 9.4716059,
                address = "Via Giuseppe Verdi, 18, Calusco d'Adda",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://cineteatrocalusco.it/"
            ),
            Cinema(
                id = 316,
                name = "Arena Cinema Palma",
                latitude = 42.1561229,
                longitude = 12.2483654,
                address = "Trevignano Romano",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cinemapalma.com/"
            ),
            Cinema(
                id = 317,
                name = "Multiplex Cinema",
                latitude = 41.6761614,
                longitude = 12.4970092,
                address = "Via della Motomeccanica, 4",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "http://www.multiplexcinemapomezia.it/"
            ),
            Cinema(
                id = 318,
                name = "Columbia Multisala",
                latitude = 40.0883574,
                longitude = 16.2087554,
                address = "Via Passeggeri, Francavilla in Sinni",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://columbiamultisala.it/index.html"
            ),
            Cinema(
                id = 319,
                name = "Sala Argentia",
                latitude = 45.5327791,
                longitude = 9.4032916,
                address = "Via Giacomo Matteotti, 30, Gorgonzola",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "www.argentia.it"
            ),
            Cinema(
                id = 320,
                name = "Multisala Aurelia Antica",
                latitude = 42.7428689,
                longitude = 11.1067928,
                address = "Via Aurelia Antica, 46, Grosseto",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.aureliaanticamultisala.it"
            ),
            Cinema(
                id = 321,
                name = "Cinema Dante",
                latitude = 45.4847494,
                longitude = 12.2352242,
                address = "Via Sernaglia, 10, Venezia",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.comune.venezia.it/it/luoghi-eventi/cinema-dante-dessai"
            ),
            Cinema(
                id = 322,
                name = "Città del Cinema Vasto",
                latitude = 42.1218684,
                longitude = 14.7083896,
                address = "Corso Europa, Vasto",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://cittadelcinemavasto.it/?playlist=cd48870&video=5a7bbdd"
            ),
            Cinema(
                id = 323,
                name = "Multisala Goldoni",
                latitude = 43.8676714,
                longitude = 10.2521503,
                address = "Via San Francesco, 124, Viareggio",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "http://www.viareggiocinema.com/multisala-goldoni/"
            ),
            Cinema(
                id = 324,
                name = "UCI Luxe",
                latitude = 41.8067248,
                longitude = 12.4864407,
                address = "Via Laurentina, 865, Roma",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.ucicinemas.it/"
            ),
            Cinema(
                id = 325,
                name = "Clev Village",
                latitude = 43.0202797,
                longitude = 11.9050308,
                address = "Strada Statale 146 di Chianciano, 30, Chiusi",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.clevillage.it/"
            ),
            Cinema(
                id = 326,
                name = "Cinema \"Il Proscenio\"",
                latitude = 41.5948759,
                longitude = 14.233115,
                address = "Via Alcide De Gasperi, 13",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://www.salvatoremincioneguarino.com/"
            ),
            Cinema(
                id = 327,
                name = "Teatro Cinema Italia",
                latitude = 43.775708,
                longitude = 11.4387758,
                address = "Via Tanzini, 48, Pontassieve",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.teatrocinemaitalia.it"
            ),
            Cinema(
                id = 328,
                name = "Ferrari",
                latitude = 40.0754127,
                longitude = 15.6261747,
                address = "Piazza Regina Elena, 24",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.facebook.com/cineteatroferrari/"
            ),
            Cinema(
                id = 329,
                name = "Multisala Electric",
                latitude = 45.8388075,
                longitude = 8.7042289,
                address = "Viale Ticino, 82, Gavirate",
                showtimes = listOf("16:00", "18:30", "21:00"),
                movieIds = happyMovieIds,
                website = "https://www.multisalaelectric.it/"
            ),
            Cinema(
                id = 330,
                name = "Cinema Castello",
                latitude = 42.7635758,
                longitude = 10.8807807,
                address = "Via Italo Calvino, 1, Castiglione della Pescaia",
                showtimes = listOf("17:30", "20:00", "22:30"),
                movieIds = energeticMovieIds,
                website = "https://castello.marte.18tickets.it/"
            ),
            Cinema(
                id = 331,
                name = "Cinema Odeon 6",
                latitude = 42.8603166,
                longitude = 13.5786175,
                address = "Viale Marcello Federici, 82, Ascoli Piceno",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.odeon6.it/"
            ),
            Cinema(
                id = 332,
                name = "Nuovo Cinema Aquila",
                latitude = 41.890419,
                longitude = 12.5255786,
                address = "Via L'Aquila, 68, Roma",
                showtimes = listOf("16:30", "19:00", "21:30"),
                movieIds = allMovieIds.shuffled().take(3),
                website = "https://www.cinemaaquila.it/"
            ),
            Cinema(
                id = 333,
                name = "Cinema Eliseo Cesena",
                latitude = 44.1395,
                longitude = 12.2468,
                address = "Viale Giosuè Carducci, 7, Cesena",
                showtimes = listOf("15:00", "18:00", "21:00"),
                movieIds = allMovieIds.shuffled().take(2),
                website = "https://www.cinemaeliseo.it/"
            )
        )
    }
}
