package com.example.readmatetasks.data.repository

import com.example.readmatetasks.R
import com.example.readmatetasks.data.model.Book

object BookRepository {

    val books = listOf(
        Book(
            id = "1",
            title = "El Principito",
            author = "Antoine de Saint-Exupéry",
            totalPages = 96,
            coverImage = R.drawable.book1,
            authorImage = R.drawable.author1,
            synopsis = "Un piloto perdido en el desierto conoce a un pequeño príncipe proveniente de otro planeta. A través de sus conversaciones, el príncipe revela profundas reflexiones sobre la vida, el amor y la naturaleza humana. Es un cuento filosófico y poético que invita a ver el mundo con los ojos de un niño.",
            about = "Publicado en 1943, 'El Principito' es una obra emblemática traducida a más de 300 idiomas. Combina elementos autobiográficos del autor y reflexiona sobre la inocencia, el amor y la pérdida."
        ),
        Book(
            id = "2",
            title = "El Conde de Montecristo",
            author = "Alexandre Dumas",
            totalPages = 1312,
            coverImage = R.drawable.book2,
            authorImage = R.drawable.author2,
            synopsis = "Edmond Dantès es traicionado por sus amigos y encarcelado injustamente. Tras años de encierro, escapa y encuentra un fabuloso tesoro que le permite transformarse en el Conde de Montecristo. Su misión será vengarse de quienes lo arruinaron, en una épica historia de traición, justicia y redención.",
            about = "Considerada una de las mejores novelas de aventuras, fue publicada en 1844 y es conocida por sus intrincadas tramas y exploración de temas como la justicia, la venganza y el perdón."
        ),
        Book(
            id = "3",
            title = "Siddhartha",
            author = "Hermann Hesse",
            totalPages = 152,
            coverImage = R.drawable.book3,
            authorImage = R.drawable.author3,
            synopsis = "Siddhartha, hijo de un brahmán, abandona su vida acomodada para buscar el verdadero significado de la existencia. A lo largo de su viaje espiritual, conoce maestros, sabios y enfrenta tentaciones. Su búsqueda lo llevará a comprender que la iluminación no se encuentra en enseñanzas externas, sino en la experiencia personal.",
            about = "Publicada en 1922, 'Siddhartha' es una obra fundamental en la literatura espiritual, influenciada por la filosofía oriental y la vida del Buda histórico."
        ),
        Book(
            id = "4",
            title = "Cien años de soledad",
            author = "Gabriel García Márquez",
            totalPages = 417,
            coverImage = R.drawable.book4,
            authorImage = R.drawable.author4,
            synopsis = "La saga de la familia Buendía en el mítico pueblo de Macondo, donde realidad y fantasía se entrelazan. A lo largo de varias generaciones, los personajes enfrentan amores prohibidos, guerras, tragedias y soledad. Una obra maestra del realismo mágico que explora la condición humana y la inevitabilidad del destino.",
            about = "Publicado en 1967, es la obra cumbre del realismo mágico. Ha sido traducida a decenas de idiomas y es considerada una de las más importantes del siglo XX."
        ),
        Book(
            id = "5",
            title = "Matar a un ruiseñor",
            author = "Harper Lee",
            totalPages = 281,
            coverImage = R.drawable.book5,
            authorImage = R.drawable.author5,
            synopsis = "Ambientada en el sur de Estados Unidos durante la Gran Depresión, narra la historia de Scout Finch y su hermano Jem, quienes aprenden sobre la injusticia racial y la empatía a través de las acciones de su padre, el abogado Atticus Finch, que defiende a un hombre negro acusado injustamente.",
            about = "Ganadora del Premio Pulitzer en 1961, es una obra esencial en la literatura estadounidense por su crítica social y defensa de la justicia."
        ),
        Book(
            id = "6",
            title = "1984",
            author = "George Orwell",
            totalPages = 328,
            coverImage = R.drawable.book6,
            authorImage = R.drawable.author6,
            synopsis = "En un mundo distópico gobernado por el Gran Hermano, Winston Smith trabaja reescribiendo la historia para el Partido. Atrapado en un sistema totalitario que controla cada aspecto de la vida, Winston comienza a cuestionar la realidad y a buscar la verdad, enfrentándose al poder absoluto y la opresión.",
            about = "Publicada en 1949, '1984' es una distopía clásica que ha influido profundamente en la cultura y política contemporánea, introduciendo conceptos como el 'Gran Hermano' y la 'doblepensar'."
        ),
        Book(
            id = "7",
            title = "Don Quijote de la Mancha",
            author = "Miguel de Cervantes",
            totalPages = 863,
            coverImage = R.drawable.book7,
            authorImage = R.drawable.author7,
            synopsis = "La historia del caballero Don Quijote y su fiel escudero Sancho Panza. Una crítica ingeniosa y satírica de las novelas de caballería, llena de humor, locura y aventura que explora la delgada línea entre la realidad y la fantasía.",
            about = "Publicada en dos partes (1605 y 1615), es considerada la primera novela moderna y una de las mejores obras de la literatura universal."
        ),
        Book(
            id = "8",
            title = "Orgullo y prejuicio",
            author = "Jane Austen",
            totalPages = 432,
            coverImage = R.drawable.book8,
            authorImage = R.drawable.author8,
            synopsis = "La historia de Elizabeth Bennet y Mr. Darcy, marcada por malentendidos y prejuicios sociales en la Inglaterra del siglo XIX. Una crítica social elegante y una poderosa historia de amor.",
            about = "Publicada en 1813, es una de las novelas más queridas por su ingenioso retrato de la sociedad y las relaciones humanas."
        ),
        Book(
            id = "9",
            title = "Crimen y castigo",
            author = "Fyodor Dostoyevsky",
            totalPages = 671,
            coverImage = R.drawable.book9,
            authorImage = R.drawable.author9,
            synopsis = "Raskólnikov, un estudiante empobrecido, comete un asesinato con la creencia de que es moralmente justificable. La novela explora la culpa, la redención y la psicología humana en un viaje tenso y filosófico.",
            about = "Publicado en 1866, es una obra clave en la literatura rusa, abordando temas filosóficos, psicológicos y éticos profundos."
        ),
        Book(
            id = "10",
            title = "La Odisea",
            author = "Homero",
            totalPages = 541,
            coverImage = R.drawable.book10,
            authorImage = R.drawable.author10,
            synopsis = "Las aventuras épicas de Odiseo en su largo regreso a Ítaca después de la guerra de Troya. Una obra clásica llena de dioses, monstruos y lecciones sobre la perseverancia y la astucia humana.",
            about = "Escrita alrededor del siglo VIII a.C., es una de las epopeyas más antiguas y fundamentales de la literatura clásica."
        ),
        Book(
            id = "11",
            title = "El Gran Gatsby",
            author = "F. Scott Fitzgerald",
            totalPages = 180,
            coverImage = R.drawable.book11,
            authorImage = R.drawable.author11,
            synopsis = "Una crítica de la alta sociedad estadounidense de los años 20 a través de la vida de Jay Gatsby y su obsesión por el amor perdido. Un retrato del sueño americano y sus excesos.",
            about = "Publicada en 1925, es una crítica del sueño americano y un retrato de la decadencia social y moral de la época."
        ),
        Book(
            id = "12",
            title = "La metamorfosis",
            author = "Franz Kafka",
            totalPages = 200,
            coverImage = R.drawable.book12,
            authorImage = R.drawable.author12,
            synopsis = "La historia de Gregor Samsa, quien un día despierta transformado en un insecto. Una alegoría sobre la alienación, la identidad y la soledad.",
            about = "Publicada en 1915, es una obra clave del existencialismo y el modernismo, símbolo de la condición humana y la incomunicación."
        ),
        Book(
            id = "13",
            title = "El retrato de Dorian Gray",
            author = "Oscar Wilde",
            totalPages = 320,
            coverImage = R.drawable.book13,
            authorImage = R.drawable.author13,
            synopsis = "Dorian Gray, un joven hermoso, vende su alma para conservar su juventud eterna mientras su retrato envejece y refleja su corrupción interior. Una crítica al hedonismo y la superficialidad.",
            about = "Publicado en 1890, es una obra icónica que critica el hedonismo y la superficialidad de la sociedad victoriana."
        ),
        Book(
            id = "14",
            title = "La Divina Comedia",
            author = "Dante Alighieri",
            totalPages = 798,
            coverImage = R.drawable.book14,
            authorImage = R.drawable.author14,
            synopsis = "Dante viaja a través del Infierno, el Purgatorio y el Paraíso en busca de la redención. Una obra monumental de la literatura que explora el pecado, la fe y la naturaleza humana.",
            about = "Escrita entre 1308 y 1320, es uno de los mayores logros literarios de la humanidad, con una profunda influencia teológica, filosófica y cultural."
        ),
        Book(
            id = "15",
            title = "Fahrenheit 451",
            author = "Ray Bradbury",
            totalPages = 249,
            coverImage = R.drawable.book15,
            authorImage = R.drawable.author15,
            synopsis = "En un futuro opresivo donde los libros están prohibidos y quemados, el bombero Montag comienza a cuestionar su papel en la sociedad y a buscar conocimiento y libertad.",
            about = "Publicada en 1953, es una advertencia contra la censura y el conformismo, considerada un clásico de la ciencia ficción distópica."
        ),
        Book(
            id = "16",
            title = "Los miserables",
            author = "Victor Hugo",
            totalPages = 1232,
            coverImage = R.drawable.book16,
            authorImage = R.drawable.author16,
            synopsis = "La historia de Jean Valjean, un exconvicto en busca de redención, y su lucha contra la injusticia social en la Francia del siglo XIX. Un relato épico sobre la compasión, la fe y la esperanza.",
            about = "Publicada en 1862, es una epopeya social y política que aborda temas de justicia, amor y esperanza."
        ),




        Book(
            id = "17",
            title = "La Iliada",
            author = "Homero",
            totalPages = 704,
            coverImage = R.drawable.book17,
            authorImage = R.drawable.author10,
            synopsis = "El poema épico que narra los eventos de la guerra de Troya, centrado en la ira de Aquiles y las batallas entre griegos y troyanos. Una obra cargada de heroísmo, honor y tragedia.",
            about = "Compuesta en el siglo VIII a.C., es una de las obras más antiguas de la literatura occidental y un pilar de la épica clásica."
        ),
        Book(
            id = "18",
            title = "Jane Eyre",
            author = "Charlotte Brontë",
            totalPages = 532,
            coverImage = R.drawable.book18,
            authorImage = R.drawable.author17,
            synopsis = "Jane Eyre, una joven huérfana, enfrenta adversidades y lucha por su independencia mientras vive un intenso romance con el misterioso Sr. Rochester.",
            about = "Publicada en 1847, es una obra precursora del feminismo por su retrato de la fuerza interior y dignidad de la protagonista."
        ),
        Book(
            id = "19",
            title = "El lobo estepario",
            author = "Hermann Hesse",
            totalPages = 312,
            coverImage = R.drawable.book19,
            authorImage = R.drawable.author3,
            synopsis = "Harry Haller, un hombre dividido entre su humanidad y su naturaleza salvaje, atraviesa una profunda crisis existencial, explorando el aislamiento y la dualidad del ser.",
            about = "Publicado en 1927, es una de las obras más influyentes de Hesse, abordando temas de alienación y autoconocimiento."
        ),
        Book(
            id = "20",
            title = "Drácula",
            author = "Bram Stoker",
            totalPages = 488,
            coverImage = R.drawable.book20,
            authorImage = R.drawable.author18,
            synopsis = "El conde Drácula viaja a Inglaterra para expandir su reino de vampiros, enfrentándose al profesor Van Helsing y sus aliados en una lucha entre el bien y el mal.",
            about = "Publicada en 1897, es la obra definitiva sobre vampiros, influyendo en toda la literatura y cine de terror posterior."
        ),
        Book(
            id = "21",
            title = "Frankenstein",
            author = "Mary Shelley",
            totalPages = 280,
            coverImage = R.drawable.book21,
            authorImage = R.drawable.author19,
            synopsis = "Victor Frankenstein da vida a una criatura hecha de restos humanos, solo para enfrentarse a las consecuencias de jugar a ser Dios en un relato trágico y filosófico.",
            about = "Publicada en 1818, es considerada la primera novela de ciencia ficción, cuestionando los límites de la ética científica."
        ),
        Book(
            id = "22",
            title = "La sombra del viento",
            author = "Carlos Ruiz Zafón",
            totalPages = 576,
            coverImage = R.drawable.book22,
            authorImage = R.drawable.author20,
            synopsis = "En la Barcelona de la posguerra, Daniel descubre un misterioso libro que lo llevará a desentrañar secretos ocultos y conspiraciones que marcarán su destino.",
            about = "Publicada en 2001, es la primera entrega de la saga 'El Cementerio de los Libros Olvidados', aclamada por su atmósfera y narrativa."
        ),
        Book(
            id = "23",
            title = "Anna Karenina",
            author = "Leo Tolstoy",
            totalPages = 864,
            coverImage = R.drawable.book23,
            authorImage = R.drawable.author21,
            synopsis = "Anna Karenina desafía las normas sociales del siglo XIX con su apasionado amor prohibido, enfrentando las consecuencias en una Rusia rígida y moralista.",
            about = "Publicada en 1877, es una obra maestra de la literatura rusa, examinando la hipocresía social, la pasión y el deber."
        ),
        Book(
            id = "24",
            title = "El guardián entre el centeno",
            author = "J.D. Salinger",
            totalPages = 277,
            coverImage = R.drawable.book24,
            authorImage = R.drawable.author22,
            synopsis = "Holden Caulfield, un joven rebelde, deambula por Nueva York tras ser expulsado del colegio, en una búsqueda de identidad y autenticidad.",
            about = "Publicada en 1951, es una novela icónica sobre la adolescencia y el desencanto, que ha marcado a generaciones."
        ),
        Book(
            id = "25",
            title = "Rayuela",
            author = "Julio Cortázar",
            totalPages = 736,
            coverImage = R.drawable.book25,
            authorImage = R.drawable.author23,
            synopsis = "Horacio Oliveira recorre París y Buenos Aires en una novela experimental que rompe con la narrativa lineal, invitando al lector a decidir el orden de la historia.",
            about = "Publicada en 1963, es una obra clave del boom latinoamericano, famosa por su estructura lúdica y profunda reflexión existencial."
        ),
        Book(
            id = "26",
            title = "La insoportable levedad del ser",
            author = "Milan Kundera",
            totalPages = 320,
            coverImage = R.drawable.book26,
            authorImage = R.drawable.author24,
            synopsis = "Ambientada en la Praga de la Primavera, narra las complejidades del amor, la política y la existencia a través de sus protagonistas.",
            about = "Publicada en 1984, es una obra filosófica que explora la libertad, el peso de las decisiones y la fugacidad de la vida."
        ),
        Book(
            id = "27",
            title = "El Aleph",
            author = "Jorge Luis Borges",
            totalPages = 157,
            coverImage = R.drawable.book27,
            authorImage = R.drawable.author25,
            synopsis = "Una colección de cuentos donde Borges juega con la metafísica, la literatura y el infinito, destacando el relato del Aleph, un punto donde se concentran todos los lugares del mundo.",
            about = "Publicado en 1949, es uno de los libros más representativos de Borges, influyendo en la literatura universal con su estilo único."
        ),
        Book(
            id = "28",
            title = "Las aventuras de Tom Sawyer",
            author = "Mark Twain",
            totalPages = 274,
            coverImage = R.drawable.book28,
            authorImage = R.drawable.author26,
            synopsis = "Tom Sawyer vive emocionantes aventuras junto a su amigo Huckleberry Finn en el río Mississippi, en un retrato inolvidable de la infancia y la libertad.",
            about = "Publicado en 1876, es un clásico de la literatura estadounidense que captura la esencia de la juventud y la vida rural."
        ),
        Book(
            id = "29",
            title = "El Hobbit",
            author = "J.R.R. Tolkien",
            totalPages = 310,
            coverImage = R.drawable.book29,
            authorImage = R.drawable.author27,
            synopsis = "Bilbo Bolsón es arrastrado a una aventura inesperada con un grupo de enanos para recuperar un tesoro custodiado por el dragón Smaug.",
            about = "Publicado en 1937, es la precuela de 'El Señor de los Anillos' y uno de los pilares de la literatura fantástica moderna."
        ),
        Book(
            id = "30",
            title = "El Señor de los Anillos: La Comunidad del Anillo",
            author = "J.R.R. Tolkien",
            totalPages = 423,
            coverImage = R.drawable.book30,
            authorImage = R.drawable.author27,
            synopsis = "Frodo Bolsón emprende un peligroso viaje para destruir un anillo que amenaza con sumir el mundo en la oscuridad, acompañado por una hermandad de héroes.",
            about = "Publicado en 1954, es el inicio de una de las sagas más influyentes del género fantástico, rica en mitología y aventura."
        ),
        Book(
            id = "31",
            title = "Las mil y una noches",
            author = "Anónimo",
            totalPages = 912,
            coverImage = R.drawable.book31,
            authorImage = R.drawable.author28,
            synopsis = "Una recopilación de cuentos mágicos y exóticos narrados por Sherezade para evitar su ejecución, incluyendo relatos como Aladino y Simbad el Marino.",
            about = "De origen oriental, estas historias han fascinado a lectores durante siglos con su mezcla de fantasía, sabiduría y misterio."
        ),
        Book(
            id = "32",
            title = "Ulises",
            author = "James Joyce",
            totalPages = 732,
            coverImage = R.drawable.book32,
            authorImage = R.drawable.author29,
            synopsis = "Un día en la vida de Leopold Bloom en Dublín, narrado con una técnica innovadora y un estilo que refleja los pensamientos y sensaciones del protagonista.",
            about = "Publicada en 1922, es una de las obras más importantes del modernismo literario, famosa por su complejidad y experimentación narrativa."
        ),
        Book(
            id = "33",
            title = "En busca del tiempo perdido",
            author = "Marcel Proust",
            totalPages = 4215,
            coverImage = R.drawable.book33,
            authorImage = R.drawable.author30,
            synopsis = "Una introspectiva y extensa exploración de la memoria, el tiempo y la sociedad francesa a través de los recuerdos del narrador.",
            about = "Publicada entre 1913 y 1927, es una obra monumental considerada una de las más influyentes del siglo XX."
        ),
        Book(
            id = "34",
            title = "Alicia en el país de las maravillas",
            author = "Lewis Carroll",
            totalPages = 200,
            coverImage = R.drawable.book34,
            authorImage = R.drawable.author31,
            synopsis = "Alicia cae por una madriguera y entra en un mundo surrealista donde nada es lo que parece. Una historia cargada de lógica absurda y personajes extravagantes.",
            about = "Publicada en 1865, es una obra icónica de la literatura infantil y una alegoría del crecimiento y la curiosidad."
        ),
        Book(
            id = "35",
            title = "La tregua",
            author = "Mario Benedetti",
            totalPages = 192,
            coverImage = R.drawable.book35,
            authorImage = R.drawable.author32,
            synopsis = "Martín Santomé, un hombre gris y rutinario, encuentra inesperadamente el amor y la felicidad en la antesala de su jubilación.",
            about = "Publicada en 1960, es una novela emblemática de la literatura latinoamericana por su sensibilidad y crítica social."
        ),
        Book(
            id = "36",
            title = "Pedro Páramo",
            author = "Juan Rulfo",
            totalPages = 124,
            coverImage = R.drawable.book36,
            authorImage = R.drawable.author33,
            synopsis = "Juan Preciado viaja al pueblo de Comala en busca de su padre, Pedro Páramo, solo para encontrarse con un lugar habitado por fantasmas y recuerdos.",
            about = "Publicada en 1955, es una obra cumbre del realismo mágico y precursora de la literatura latinoamericana contemporánea."
        ),
        Book(
            id = "37",
            title = "El Amor en los Tiempos del Cólera",
            author = "Gabriel García Márquez",
            totalPages = 368,
            coverImage = R.drawable.book37,
            authorImage = R.drawable.author4,
            synopsis = "Florentino Ariza y Fermina Daza viven una historia de amor que atraviesa décadas, marcada por la paciencia, el deseo y la espera, en un Caribe lleno de pasión y nostalgia.",
            about = "Publicada en 1985, es una obra esencial de García Márquez que explora el amor eterno y la persistencia a lo largo del tiempo."
        ),
        Book(
            id = "38",
            title = "Memorias de Adriano",
            author = "Marguerite Yourcenar",
            totalPages = 410,
            coverImage = R.drawable.book38,
            authorImage = R.drawable.author34,
            synopsis = "Las memorias ficticias del emperador romano Adriano, quien reflexiona sobre el poder, la filosofía y la fragilidad humana en el ocaso de su vida.",
            about = "Publicada en 1951, es una novela histórica aclamada por su profundidad y minuciosa recreación del mundo romano."
        ),
        Book(
            id = "39",
            title = "La Casa de los Espíritus",
            author = "Isabel Allende",
            totalPages = 496,
            coverImage = R.drawable.book39,
            authorImage = R.drawable.author35,
            synopsis = "La saga de la familia Trueba a través de varias generaciones, donde la política, el amor y los espíritus configuran un relato apasionante lleno de realismo mágico.",
            about = "Publicada en 1982, es la obra debut de Allende y un clásico de la literatura latinoamericana contemporánea."
        ),
        Book(
            id = "40",
            title = "Hijos de Húrin",
            author = "J.R.R. Tolkien",
            totalPages = 320,
            coverImage = R.drawable.book40,
            authorImage = R.drawable.author27,
            synopsis = "La trágica historia de Túrin Turambar y su familia, marcada por una maldición, en la Primera Edad de la Tierra Media.",
            about = "Publicada póstumamente en 2007, expande el legendarium de Tolkien, con un tono oscuro y épico."
        ),
        Book(
            id = "41",
            title = "Retrato en Sepia",
            author = "Isabel Allende",
            totalPages = 368,
            coverImage = R.drawable.book41,
            authorImage = R.drawable.author35,
            synopsis = "La joven Aurora del Valle reconstruye su pasado familiar mientras busca su identidad en el Chile del siglo XIX.",
            about = "Publicada en 2000, es parte de la saga familiar de Allende, rica en contexto histórico y personajes femeninos fuertes."
        ),
        Book(
            id = "42",
            title = "Cuentos de Borges",
            author = "Jorge Luis Borges",
            totalPages = 300,
            coverImage = R.drawable.book42,
            authorImage = R.drawable.author25,
            synopsis = "Una colección que reúne varios cuentos emblemáticos de Borges, donde el tiempo, los laberintos y la metaficción son protagonistas.",
            about = "Incluye relatos esenciales como 'La Biblioteca de Babel', 'El jardín de senderos que se bifurcan' y más, pilares de la literatura universal."
        ),
        Book(
            id = "43",
            title = "Demian",
            author = "Hermann Hesse",
            totalPages = 224,
            coverImage = R.drawable.book43,
            authorImage = R.drawable.author3,
            synopsis = "Emil Sinclair narra su paso de la infancia a la adultez bajo la influencia de su enigmático amigo Demian, en una búsqueda interior por la verdad y la autenticidad.",
            about = "Publicada en 1919, es una obra clave en la literatura iniciática y existencialista, influida por el psicoanálisis y la filosofía."
        ),
        Book(
            id = "44",
            title = "Viaje al Centro de la Tierra",
            author = "Jules Verne",
            totalPages = 312,
            coverImage = R.drawable.book44,
            authorImage = R.drawable.author36,
            synopsis = "El profesor Lidenbrock, su sobrino Axel y su guía emprenden una fascinante expedición al interior del planeta, enfrentándose a maravillas y peligros subterráneos.",
            about = "Publicada en 1864, es una de las aventuras clásicas más conocidas de Verne, mezcla de ciencia y ficción desbordante."
        ),
        Book(
            id = "45",
            title = "20,000 Leguas de Viaje Submarino",
            author = "Jules Verne",
            totalPages = 416,
            coverImage = R.drawable.book45,
            authorImage = R.drawable.author36,
            synopsis = "El Capitán Nemo y su submarino Nautilus llevan a sus pasajeros a través de los océanos, en una épica travesía llena de maravillas y misterios.",
            about = "Publicada en 1870, es una obra icónica de la literatura de aventuras y ciencia ficción del siglo XIX."
        ),
        Book(
            id = "46",
            title = "Cuentos Completos",
            author = "Edgar Allan Poe",
            totalPages = 560,
            coverImage = R.drawable.book46,
            authorImage = R.drawable.author37,
            synopsis = "Una recopilación definitiva de los relatos de Poe, que abarcan el terror, la locura, el misterio y lo macabro, con clásicos como 'El cuervo' y 'El corazón delator'.",
            about = "Edgar Allan Poe es considerado el maestro del cuento corto y el pionero del género de terror y detectivesco en la literatura moderna."
        )






    )

    fun getAllBooks(): List<Book> = books

    fun getBookById(bookId: String): Book? = books.find { it.id == bookId }
}
