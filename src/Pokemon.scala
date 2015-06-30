case class Pokemon(experiencia:Int, genero:Genero,energia:Int,energiaMaximaOriginal:Int,pesoOriginal:Int,
    fuerzaOriginal:Int,velocidadOriginal:Int,estado:Estado,especie:Especie,ataques:List[Ataque]) {
  
  type Actividad=Object=>Pokemon;
  
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
  
  
  val realizarUnAtaque:Actividad={
      case ataque:Ataque => ataque.aplicarEfecto( this.subirExperiencia(ataque.tipo match{
        case Dragon => 80
        case this.especie.tipoPrincipal => 50
        case this.especie.tipoSecundario => this.genero match{
          case Macho => 20
          case Hembra => 40
        }
      }))
  }
  
}