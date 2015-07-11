package pokemon


class CaracteristicasInvalidasException(e: String = "Caracteristicas Invalidas") extends RuntimeException(e)
class NivelIncorrectoException(e: String = "Nivel Invalido") extends RuntimeException(e)
class PesoInvalidoException(e: String = "Peso Invalido") extends RuntimeException(e)
class FuerzaInvalidaException(e: String = "Fuerza menor a 1") extends RuntimeException(e)
class VelocidadInvalidaException(e: String = "Velocidad menor a 1") extends RuntimeException(e)
class NoExisteRutinaApropiadaException(e: String = "Ninguna rutina aplica al pokemon") extends RuntimeException(e)
class KOException(e: String = "No se pudo realizar la actividad, el pokemon esta KO") extends RuntimeException(e)
class NoSePuedeRealizarElAtaqueException(e: String = "No se puede realizar el ataque, no lo conoce el pokemon") extends RuntimeException(e)
class NoSePudoLevantarPesasException(e: String = "No se puedo levantar Pesas") extends RuntimeException(e)