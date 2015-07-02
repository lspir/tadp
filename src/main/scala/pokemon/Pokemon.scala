package pokemon

import scala.util.Try


case class Pokemon(experiencia:Int, genero:Genero,energia:Int,energiaMaximaOriginal:Int,pesoOriginal:Int,
    fuerzaOriginal:Int,velocidadOriginal:Int,estado:Estado,especie:Especie,ataques:List[Ataque]) {
  
    
  val nivel:Int=especie.obtenerNivel(experiencia);
  val energiaMaxima=especie.aumentar(energiaMaximaOriginal,nivel);
  val peso=especie.aumentar(pesoOriginal,nivel);
  val fuerza=especie.aumentar(fuerzaOriginal,nivel);
  val velocidad=especie.aumentar(velocidadOriginal,nivel);
  
  
  def subirExperiencia(exp:Int):Pokemon={
    var pokemonExperimentado = copy(experiencia=this.experiencia+exp)
    if (this.nivel<pokemonExperimentado.nivel){
      pokemonExperimentado=this.evolucionarSiCorrespondeNivel(pokemonExperimentado);
    }
    return pokemonExperimentado;
  }
  
  def evolucionarSiCorrespondeNivel(pokemon:Pokemon):Pokemon= {
    val especieNueva=especie.evolucionarSiCorrespondeNivel(pokemon)
  pokemon.copy(especie=especieNueva)
  }
  
  
}