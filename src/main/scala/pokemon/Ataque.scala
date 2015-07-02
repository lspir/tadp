package pokemon

import scala.util.Try

case class Ataque(tipo:Tipo,maximoInicial:Int,efecto:Option[(Pokemon=>Pokemon)]=None) {
  def aplicarEfecto(pokemon:Pokemon):Pokemon={
    efecto.map(f=>f(pokemon)).getOrElse(pokemon)
    }
}