
case class Especie(porcentajeIncremento:Int,resistenciaEvolutiva:Int,pesoMaximo:Int,
    tipoPrincipal:Tipo,tipoSecundario:Tipo=null,evolucion:Evolucion=null) {
  
  def obtenerExperienciaNivel(nivel:Int):Int= nivel match{
      case 1 => 0
      case _ => (2 * this.obtenerExperienciaNivel(nivel-1)) + this.resistenciaEvolutiva
  }
  
    def obtenerNivel(experiencia : Int) : Int = {
    val niveles = (1 to 100).toList
    niveles.filter { nivel => obtenerExperienciaNivel(nivel)<=experiencia }.last
  }
    
  def aumentar(caracteristica:Int,nivel:Int):Int= nivel match{
    case 1 => caracteristica;
    case _ => aumentar(caracteristica,nivel-1)+aumentar(caracteristica,nivel-1)*porcentajeIncremento/100;
  }  
}