package pokemon

case class Especie(porcentajeIncremento:Int,resistenciaEvolutiva:Int,pesoMaximo:Int,
    tipoPrincipal:Tipo,tipoSecundario:Option[Tipo]=None,evolucion:Option[Evolucion]=None) {
  
  def obtenerExperienciaParaNivel(nivel:Int): Int= nivel match{
      case 1 => 0
      case _ => (2 * this.obtenerExperienciaParaNivel(nivel-1)) + this.resistenciaEvolutiva
  }
  
    def obtenerNivel(experiencia : Int) : Int = {
    val niveles = (1 to 100).toList
    niveles.find { nivel => this.experienceBetween(nivel,experiencia) }.getOrElse(100)
  }
    def experienceBetween(nivel:Int,experiencia:Int):Boolean={
      obtenerExperienciaParaNivel(nivel)<=experiencia && experiencia < obtenerExperienciaParaNivel(nivel+1)
    }
    
  def aumentar(caracteristica:Int,nivel:Int):Int= nivel match{
    case 1 => caracteristica;
    case _ => aumentar(caracteristica,nivel-1)+aumentar(caracteristica,nivel-1)*porcentajeIncremento/100;
  }  
  
  def evolucionarSiCorrespondeNivel(pokemon:Pokemon):Especie={
  evolucion.filter { ev => ev.cumpleCondicionNivel(pokemon) }.map { ev => ev.especie }.getOrElse(this)
  }
  
  def sosDeTipo(tipo:Tipo):Boolean={
    tipoPrincipal.equals(tipo) || tipoSecundario.contains(tipo)
  }
  def perdesContra(tipo:Tipo):Boolean = {
    tipoPrincipal.perdesContra(tipo) || tipoSecundario.exists{_.perdesContra(tipo)}
  }
  def sosAfin(tipo:Tipo):Boolean={
    (tipo==tipoPrincipal || tipo == tipoSecundario || tipo == TipoNormal)
  }
  
}