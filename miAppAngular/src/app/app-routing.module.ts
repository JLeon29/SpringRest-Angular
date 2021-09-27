import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoriasListarComponent } from './components/categorias-listar/categorias-listar.component';
import { MarcasListarComponent } from './components/marcas-listar/marcas-listar.component';
import { ZapatillaListarComponent } from './components/zapatilla-listar/zapatilla-listar.component';
import { ZapatillaActualizarComponent } from './components/zapatilla-actualizar/zapatilla-actualizar.component';
import { MarcaActualizarComponent } from './components/marca-actualizar/marca-actualizar.component';
import { CategoriaActualizarComponent } from './components/categoria-actualizar/categoria-actualizar.component';
import { MarcaNewComponent } from './components/marca-new/marca-new.component';
import { CategoriaNewComponent } from './components/categoria-new/categoria-new.component';
import { ZapatillaNewComponent } from './components/zapatilla-new/zapatilla-new.component';

const routes: Routes = [

  {path: 'listarCategorias', component: CategoriasListarComponent},
  {path: '', redirectTo:'listarZapatillas', pathMatch:'full' },
  {path: 'listarCategorias/actualizar/:id', component: CategoriaActualizarComponent},
  {path: 'listarMarcas', component: MarcasListarComponent},
  {path: 'listarZapatillas', component: ZapatillaListarComponent},
  {path: 'listarZapatillas/actualizar/:id', component: ZapatillaActualizarComponent},
  {path: 'listarMarcas/actualizar/:id', component: MarcaActualizarComponent},
  {path: 'listarMarcas/crear', component: MarcaNewComponent},
  {path: 'listarCategorias/crear', component: CategoriaNewComponent},
  {path: 'listarZapatillas/crear', component: ZapatillaNewComponent},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
