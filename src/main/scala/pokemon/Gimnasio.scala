package pokemon

import scala.util.Try
import scala.util.Failure
import scala.util.Failure
import scala.util.Success

case object Gimnasio {
  
  type Actividad[T]=T=>Pokemon=>Try[Pokemon];
  type ActividadSinParametro=Pokemon=>Try[Pokemon];
  
    val realizarUnAtaque:Actividad[Ataque]={
      case ataque:Ataque => {
        case pokemon:Pokemon if pokemon.ataques.contains(ataque) => Try(pokemon.usarAtaque(ataque).subirExperiencia(ataque.tipo match{
          case Dragon => 80
          case pokemon.especie.tipoPrincipal => 50
          case pokemon.especie.tipoSecundario => pokemon.genero match{
            case Macho => 20
            case Hembra => 40
          } 
        }))
      case _:Pokemon=> Failure(new Exception)}
   }

 
  def realizar[T](actividad:(Pokemon=>Try[Pokemon]),pokemon:Pokemon):Try[Pokemon]={
    pokemon.estado match {
      case e:Dormido => Try(pokemon.copy(estado = e.dormir))
      case KO => Failure(new Exception)
      case _ => actividad(pokemon)
    }
  }  
  
  
  val levantarPesas:Actividad[Int]={
    case kilos:Int => {
      case pokemon:Pokemon=>pokemon.estado match{
        case _ if pokemon.especie.sosDeTipo(Fantasma) => Failure(new Exception)
        case Paralizado => Try(pokemon.copy(estado=KO))
        case _ if kilos>pokemon.fuerza*10 => Try(pokemon.copy(estado=Paralizado))
        case _ if pokemon.especie.sosDeTipo(Pelea)=>Try(pokemon.subirExperiencia(2*kilos))
        case _ => Try(pokemon.subirExperiencia(kilos))
      }
    }
  }
  val nadar:Actividad[Int]={
    case minutos:Int => {
      case pokemon:Pokemon if pokemon.especie.perdesContra(Agua) => Try(pokemon.copy(estado = KO))
      case pokemon:Pokemon if pokemon.especie.sosDeTipo(Agua) => Try(pokemon.copy(velocidadOriginal = pokemon.velocidad + minutos/60, energiaOriginal = pokemon.energia + minutos).subirExperiencia(200*minutos))
      case pokemon:Pokemon => Try(pokemon.copy(energiaOriginal = pokemon.energia - minutos).subirExperiencia(200*minutos))
    }
  }
  
  val aprenderAtaque:Actividad[Ataque]={
    case ataque:Ataque=>{
      case pokemon:Pokemon=> {
        Try(if (pokemon.especie.sosAfin(ataque.tipo)) pokemon.aprende(ataque)
                             else pokemon.copy(estado=KO))
                             }
     }
   }
   val usarPocion:ActividadSinParametro={
     case pokemon:Pokemon => {
       Try(pokemon.copy(energiaOriginal = pokemon.energia + 50))
     }
   }
   val usarAntidoto:ActividadSinParametro={
     case pokemon:Pokemon => Try(pokemon.estado match{
       case Envenenado => pokemon.copy(estado = EstadoNormal)
       case _ => pokemon
     })
   }
   val usarEther:ActividadSinParametro={
     case pokemon:Pokemon => Try(pokemon.estado match {
       case KO => pokemon
       case _ => pokemon.copy(estado = EstadoNormal)
     }) 
   }
   val comerHierro:ActividadSinParametro={
     case p:Pokemon => Try(p.copy(fuerzaOriginal = p.fuerzaOriginal + 5))
   }
   val comerCalcio:ActividadSinParametro={
     case p:Pokemon => Try(p.copy(velocidadOriginal = p.velocidadOriginal + 5))
   }
   val comerZinc:ActividadSinParametro={
     case p:Pokemon => Try(p.copy(ataques = p.ataques.mapValues{case (num, a)=>(num+2, a)}))
   }
   val descansar:ActividadSinParametro={
     case p:Pokemon => Try((if(p.estado==EstadoNormal && p.energia*2>p.energiaMaxima) {p.copy(estado = new Dormido)} else {p}).copy(ataques = p.ataques.mapValues {case (pa, pm)=>(pm, pm)}))
     }
   
   
     val usarPiedra:Actividad[Piedra]={
     case piedra:Piedra=>{
       case pokemon:Pokemon =>Try(pokemon.especie.evolucion.filter { ev => ev.condicion.teCumple(pokemon,piedra) }.
                                   fold(envenenarSiCorresponde(pokemon,piedra)){evolucion=>pokemon.copy(especie=evolucion.especie)})
     }
   }
   
     
   def envenenarSiCorresponde(pokemon:Pokemon,piedra:Piedra):Pokemon={
    if (pokemon.especie.teGana(piedra.tipo)) pokemon.copy(estado=Envenenado)
    else pokemon
  }
   
   
   
   val fingirIntercambio:ActividadSinParametro={
     case pokemon:Pokemon => Try(pokemon.especie.evolucion.filter { ev=> ev.condicion==Intercambiar}.
                             fold(aumentarPesoSegunGenero(pokemon)) {evolucion=>pokemon.copy(especie=evolucion.especie)})  
   }
   val aumentarPesoSegunGenero:Pokemon=>Pokemon = {
     case pokemon:Pokemon => pokemon.copy(pesoOriginal = pokemon.pesoOriginal + (pokemon.genero match{
       case Macho => +1
       case Hembra => -10
     }))
   }
   
   //Punto 3
   type Rutina = (String,List[ActividadSinParametro])
   
   def ejecutarRutina(rutina:Rutina, pokemon:Pokemon):Try[Pokemon]=rutina match{
     case (_,actividades)=>actividades.foldLeft(Try(pokemon)){(tryPokemon, actividad) => tryPokemon.flatMap { poke => actividad(poke) }}
   }
   
   //Punto 4
   type Criterio=Pokemon=>Pokemon=>Boolean
   
   def mejorRutinaSegunCriterio(rutinas:List[Rutina], pokemon:Pokemon, criterio:Criterio):String={
     val rutinasValidas = rutinas.filter{rut=>ejecutarRutina(rut, pokemon).isSuccess }
     val posibleValor= rutinasValidas.sortWith{(left,right)=>criterio(ejecutarRutina(left, pokemon).get)(ejecutarRutina(right, pokemon).get)}.headOption
     posibleValor.fold(throw new NoExisteRutinaApropiadaException){_._1}
   }
   
      
  val prueba:Int=>String=>Try[Int]={
    case i1:Int=>{case s:String=>Try(i1)}
  }
  
   
}
  



//Gimnasio.realizar(actividad(new Ataque)(pokemon))