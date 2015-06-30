case class Ataque(tipo:Tipo,maximoInicial:Int,efecto:Option[(Pokemon=>Pokemon)]) {
  def aplicarEfecto(pokemon:Pokemon):Pokemon={
    efecto.map(f=>f(pokemon)).getOrElse(pokemon)
    }
}