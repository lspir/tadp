package pokemon

import org.junit.Test
import org.junit.Assert

class TipoTest {
  @Test
  def `tipo Psiquico le gana a Pelea y Veneno, pero pierde contra Fantasma`={
    Assert.assertTrue(Psiquico.ganasContra(Pelea))
    Assert.assertTrue(Psiquico.ganasContra(Veneno))
    Assert.assertTrue(Psiquico.perdesContra(Fantasma))
  }
}