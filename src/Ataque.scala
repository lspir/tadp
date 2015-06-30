import javax.swing.text.html.Option

case class Ataque(tipo:Tipo,maximoInicial:Int,efecto:Option[(Pokemon=>Pokemon)]) {
  def aplicarEfecto(pokemon:Try[Pokemon]):Try[Pokemon]={
    efecto.map(f=>f(pokemon)).getOrElse(pokemon)
    }
}