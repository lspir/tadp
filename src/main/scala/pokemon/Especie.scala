package pokemon


case class Especie(porcentajeIncremento:Int,resistenciaEvolutiva:Long,pesoMaximo:Int,
    tipoPrincipal:Tipo,tipoSecundario:Option[Tipo]=None,evolucion:Option[Evolucion]=None) {
  require(resistenciaEvolutiva>0L,"Resistencia Evolutiva debe ser mayor que 0")
  
  def obtenerExperienciaParaNivel(nivel:Int): Long= nivel match{
      case 1 => 0L
      case _ => (2L * this.obtenerExperienciaParaNivel(nivel-1)) + this.resistenciaEvolutiva
  }
  
    def obtenerNivel(experiencia : Long) : Int = {
    val niveles = (1 to 100).toList
    niveles.find { nivel => this.experienceBetween(nivel,experiencia) }.getOrElse(100)
  }
    def experienceBetween(nivel:Int,experiencia:Long):Boolean={
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
    (tipo==tipoPrincipal || tipo == tipoSecundario.getOrElse(tipo) || tipo == TipoNormal)
  }
  def teGana(tipo:Tipo):Boolean={
    tipoPrincipal.perdesContra(tipo) || tipoSecundario.exists { _.perdesContra(tipo)}
  }
  
}