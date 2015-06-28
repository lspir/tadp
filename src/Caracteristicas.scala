
class CaracteristicasInvalidasException(e: String = "Caracteristicas Invalidas") extends RuntimeException(e)

abstract class Genero
case object Macho extends Genero
case object Hembra extends Genero
