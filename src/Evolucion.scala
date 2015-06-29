case class Evolucion(especie:Especie,condicion:CondicionEvolucion) {
  
  def cumpleCondicionNivel(pokemon:Pokemon):Boolean={
    condicion match{
      case subirDeNivel => condicion.teCumple(pokemon)
      case _ => false
    }
  } 
  
}


