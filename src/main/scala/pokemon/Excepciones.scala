package pokemon


class CaracteristicasInvalidasException(e: String = "Caracteristicas Invalidas") extends RuntimeException(e)
class NivelIncorrectoException(e: String = "Nivel Invalido") extends RuntimeException(e)
class PesoInvalidoException(e: String = "Peso Invalido") extends RuntimeException(e)
class FuerzaInvalidaException(e: String = "Fuerza menor a 1") extends RuntimeException(e)
class VelocidadInvalidaException(e: String = "Velocidad menor a 1") extends RuntimeException(e)
