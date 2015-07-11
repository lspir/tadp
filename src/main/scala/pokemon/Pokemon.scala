package pokemon

import scala.util.Try
import scala.util.Failure


case class Pokemon(experiencia:Long, genero:Genero,energiaOriginal:Int,energiaMaximaOriginal:Int,pesoOriginal:Int,
    fuerzaOriginal:Int,velocidadOriginal:Int,estado:Estado,especie:Especie,ataques:Map[Ataque,(Int,Int)]) {
  
  val fuerzaMaxima=100
  val velocidadMaxima=100
  val nivel:Int=especie.obtenerNivel(experiencia);
  val energiaMaxima=especie.aumentar(energiaMaximaOriginal,nivel);
  val energia:Int=minimo(energiaOriginal,energiaMaxima)
  val peso=especie.aumentar(pesoOriginal,nivel);
  val fuerza=minimo(especie.aumentar(fuerzaOriginal,nivel),fuerzaMaxima);
  val velocidad=minimo(especie.aumentar(velocidadOriginal,nivel),velocidadMaxima);
 
  if (nivel<1 || nivel >100) throw new NivelIncorrectoException
  if (peso<0 || peso>especie.pesoMaximo) throw new PesoInvalidoException
  if (fuerza<1) throw new FuerzaInvalidaException
  if (velocidad<1) throw new VelocidadInvalidaException
  
  def minimo(n1:Int,n2:Int):Int={
    if (n1<=n2) n1
    else n2
  }
  
  def subirExperiencia(exp:Long):Pokemon={
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
  
  def usarAtaque(ataque:Ataque):Pokemon={
    val nuevosAtaques = this.ataques.map {case (a, (pa, pm)) => if (a == ataque) (a, (pa-1, pm)) else (a, (pa, pm))}
    ataque.aplicarEfecto(copy(ataques = nuevosAtaques))
    }
  
  def aprende(ataque:Ataque):Pokemon={
    copy(ataques=this.ataques.+((ataque,(ataque.maximoInicial,ataque.maximoInicial))))
  }
  lazy val realizar:(Pokemon=>Try[Pokemon])=>Try[Pokemon]={
    case actividad:(Pokemon=>Try[Pokemon])=>{
      this.estado match {
      case e:Dormido => Try(copy(estado = e.verificacionTurnosRestantes))
      case KO => Failure(new KOException)
      case _ => actividad(this)
    }
      
    }
  }
  
  def realizarUnAtaque(ataque:Ataque):Try[Pokemon]={
      if (this.ataques.contains(ataque)) {Try(this.usarAtaque(ataque).subirExperiencia(ataque.tipo match{
          case Dragon => 80
          case this.especie.tipoPrincipal => 50
          case _:Tipo if this.especie.tipoSecundario.exists {_.equals(ataque.tipo)} => this.genero match{
            case Macho => 20
            case Hembra => 40
          } 
        }))}
      else Failure(new NoSePuedeRealizarElAtaqueException)
   }
  
  def levantarPesas(kilos:Int):Try[Pokemon]={
    this.estado match{
        case _ if this.especie.sosDeTipo(Fantasma) => Failure(new NoSePudoLevantarPesasException)
        case Paralizado => Try(copy(estado=KO))
        case _ if kilos>this.fuerza*10 => Try(copy(estado=Paralizado))
        case _ if this.especie.sosDeTipo(Pelea)=>Try(this.subirExperiencia(2*kilos))
        case _ => Try(this.subirExperiencia(kilos))
      }
  }
  
    def nadar(minutos:Int):Try[Pokemon]=this match{
      case pokemon:Pokemon if pokemon.especie.perdesContra(Agua) => Try(pokemon.copy(estado = KO))
      case pokemon:Pokemon if pokemon.especie.sosDeTipo(Agua) => Try(pokemon.copy(velocidadOriginal = pokemon.velocidad + minutos/60, energiaOriginal = pokemon.energia + minutos).subirExperiencia(200*minutos))
      case pokemon:Pokemon => Try(pokemon.copy(energiaOriginal = pokemon.energia - minutos).subirExperiencia(200*minutos))
    }
  
    def aprenderAtaque(ataque:Ataque):Try[Pokemon]={
    Try(if (this.especie.sosAfin(ataque.tipo)) this.aprende(ataque)
        else copy(estado=KO))
    }
    
   lazy val usarPocion:Try[Pokemon]={
     Try(copy(energiaOriginal = this.energia + 50) )
    }
   
   lazy  val usarAntidoto:Try[Pokemon]={
     Try(this.estado match{
       case Envenenado => copy(estado = EstadoNormal)
       case _ => this
     })
   }
     
   lazy  val usarEther:Try[Pokemon]={
     Try(this.estado match {
       case KO => this
       case _ => copy(estado = EstadoNormal)
     }) 
   }
   
   lazy val comerHierro:Try[Pokemon]={
     Try(copy(fuerzaOriginal = this.fuerzaOriginal + 5))
   }
   
   lazy val comerCalcio:Try[Pokemon]={
     Try(copy(velocidadOriginal = this.velocidadOriginal + 5))
   }
    
   lazy val comerZinc:Try[Pokemon]={
     Try(copy(ataques = this.ataques.mapValues{case (pa, max)=>if (pa+2>max) (max, max) else (pa+2, max)}))
   }
   lazy  val descansar:Try[Pokemon]={
     Try((if(this.estado==EstadoNormal && this.energia*2>this.energiaMaxima) {copy(estado = new Dormido)} else {this}).copy(ataques = this.ataques.mapValues {case (pa, pm)=>(pm, pm)}))
     }

   def usarPiedra(piedra:Piedra):Try[Pokemon]={
     Try(this.especie.evolucion.filter { ev => ev.condicion.teCumple(this,Some(piedra)) }.
                                   fold(envenenarSiCorresponde(piedra)){evolucion=>copy(especie=evolucion.especie)})
     }
        
   def envenenarSiCorresponde(piedra:Piedra):Pokemon={
    if (this.especie.teGana(piedra.tipo)) copy(estado=Envenenado)
    else this
    }
   
  lazy val fingirIntercambio:Try[Pokemon]={
     Try(this.especie.evolucion.filter { ev=> ev.condicion==Intercambiar}.
                             fold(this.aumentarPesoSegunGenero) {evolucion=>copy(especie=evolucion.especie)})  
   }
  lazy val aumentarPesoSegunGenero:Pokemon = {
     copy(pesoOriginal = this.pesoOriginal + (this.genero match{
       case Macho => +1
       case Hembra => -10
     }))
   }        

  
}
