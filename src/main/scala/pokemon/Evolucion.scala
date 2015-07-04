package pokemon

case class Evolucion(especie:Especie,condicion:CondicionEvolucion) {
  
  def cumpleCondicionNivel(pokemon:Pokemon):Boolean={
    condicion match{
      case _:SubirDeNivel => condicion.teCumple(pokemon)
      case _ => false
    }
  } 
  
}

