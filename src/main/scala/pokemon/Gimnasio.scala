package pokemon

import scala.util.Try
import scala.util.Failure
import scala.util.Failure

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
      case pokemon:Pokemon if pokemon.especie.sosDeTipo(Agua) => Try(pokemon.copy(velocidadOriginal = pokemon.velocidad + minutos/60, energia = pokemon.energia + minutos).subirExperiencia(200*minutos))
      case pokemon:Pokemon => Try(pokemon.copy(energia = pokemon.energia - minutos).subirExperiencia(200*minutos))
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
       Try(pokemon.copy(energia = pokemon.energia + 50))
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
       case pokemon:Pokemon => pokemon.especie.evolucion match{
         case Some(ev) => ev.condicion match{
           case a:CondicionUsarPiedra if (a.teCumple(pokemon)(piedra.tipo)) => Try(pokemon.copy(especie = ev.especie))
           case CondicionUsarPiedraLunar if (piedra.tipo==Lunar) => Try(pokemon.copy(especie = ev.especie))
         }
         case None => Try(pokemon)
       }
     }
   }
   val fingirIntercambio:ActividadSinParametro={
     case pokemon:Pokemon => pokemon.especie.evolucion match{
       case Some(ev) => ev.condicion match{
         case a:Intercambiar => Try(pokemon.copy(especie = ev.especie))
         case _ => Try(aumentarPesoSegunGenero(pokemon))
       }
       case _ => Try(aumentarPesoSegunGenero(pokemon))
     }  
   }
   val aumentarPesoSegunGenero:Pokemon=>Pokemon = {
     case pokemon:Pokemon => pokemon.copy(pesoOriginal = pokemon.pesoOriginal + (pokemon.genero match{
       case Macho => +1
       case Hembra => -10
     }))
   }
   
   //Punto 3
   type Rutina = List[(Try[Pokemon]=>Try[Pokemon])]
   
   def ejecutarRutina(rutina:Rutina, pokemon:Pokemon):Try[Pokemon]={
     rutina.foldLeft(Try(pokemon)){(x, y) => y(x)}
   }
   
   //Punto 4
   
   def maximoRutinaSegunCriterio(rutinas:List[Rutina], pokemon:Pokemon, criterio:Try[Pokemon]=>Int):Rutina={
     rutinas.maxBy { x => criterio(ejecutarRutina(x, pokemon)) }
   }
   
  val prueba:Int=>String=>Try[Int]={
    case i1:Int=>{case s:String=>Try(i1)}
  }
}
  



//Gimnasio.realizar(actividad(new Ataque)(pokemon))