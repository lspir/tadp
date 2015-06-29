case class Pokemon(experiencia:Int, genero:Genero,energia:Int,energiaMaximaOriginal:Int,pesoOriginal:Int,
    fuerzaOriginal:Int,velocidadOriginal:Int,estado:Estado,especie:Especie) {
  
  def nivel:Int=especie.obtenerNivel(experiencia);
  def energiaMaxima=especie.aumentar(energiaMaximaOriginal,nivel);
  def peso=especie.aumentar(pesoOriginal,nivel);
  def fuerza=especie.aumentar(fuerzaOriginal,nivel);
  def velocidad=especie.aumentar(velocidadOriginal,nivel);
  
  
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