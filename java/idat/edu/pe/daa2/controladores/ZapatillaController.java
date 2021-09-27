package idat.edu.pe.daa2.controladores;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import idat.edu.pe.daa2.entidades.Marca;
import idat.edu.pe.daa2.entidades.Zapatilla;
import idat.edu.pe.daa2.servicio.CategoriaServiceImpl;
import idat.edu.pe.daa2.servicio.ICategoriaService;
import idat.edu.pe.daa2.servicio.IMarcaService;
import idat.edu.pe.daa2.servicio.IZapatillaService;
import idat.edu.pe.daa2.servicio.MarcaServiceImpl;
import idat.edu.pe.daa2.servicio.ZapatillaServiceImpl;
import idat.edu.pe.daa2.util.Utileria;

@Controller
@RequestMapping("/zapatilla")
public class ZapatillaController {
	
	@Value("${EntreTabasMVC.ruta.imagenes}")
	private String ruta;

	@Autowired
	@Qualifier("zapatillaServiceJpa")
	private IZapatillaService zapatillaService;

	@Autowired
	@Qualifier("categoriaServiceJpa")
	private ICategoriaService categoriaService;

	@Autowired
	@Qualifier("marcaServiceJpa")
	private IMarcaService marcaService;

	
	
	@GetMapping("/mostrar")
	public String mostrarZapatillas(Model model) {
		/*
		List<Zapatilla> lista = zapatillaService.mostrar();
		model.addAttribute("zapatillas", lista);
		System.out.println(lista);
		*/
		return "shoes";

	}
	

	@GetMapping("/")
	public String mostrarInicio(Model model) {
		
		return "index";
	}

	@GetMapping("/listar")
	public String listar(Model model) {

		List<Zapatilla> lista = zapatillaService.mostrar();
		model.addAttribute("zapatillas", lista);

		return "tabla";
	}
	
	
	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") int idZapatilla, Model model) {

		Zapatilla vacante = zapatillaService.buscarPorId(idZapatilla);
		model.addAttribute("zapatilla", vacante);
		return "detalle";
	}

	@GetMapping("/crear")
	public String crearZapatilla(Zapatilla zapatilla, Model model) {

		
		return "zapatillas/formZapatillas";
	}

	@PostMapping("/save")
	public String guardarZapatilla(Zapatilla zapatilla, BindingResult result, RedirectAttributes attributes,
			@RequestParam("archivoImagen") MultipartFile multiPart) {

		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.out.println("Ocurrio un error: " + error.getDefaultMessage());

				return "zapatillas/formZapatillas";

			}

		}

		if (!multiPart.isEmpty()) {
			// String ruta = "/empleos/img-vacantes/"; // Linux/MAC
			//String ruta = "c:/entretabas/img-entretabas/"; // Windows
			String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null) { // La imagen si se subio
				// Procesamos la variable nombreImagen
				zapatilla.setImagen(nombreImagen);
			}
		}
		zapatillaService.guardar(zapatilla);
		attributes.addFlashAttribute("msg", "Registro Guardado");
		System.out.println(zapatilla);

		return "redirect:/zapatilla/listar";
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	@GetMapping("/actualizar/{id}")
	public String actualizar(@PathVariable(name="id") int idZapatilla, Model model) {
		
		Zapatilla zapatilla = zapatillaService.buscarPorId(idZapatilla);
		
		model.addAttribute("zapatilla", zapatilla);
		return "zapatillas/formZapatillas";
	}
	
	
	
	@GetMapping("/eliminar/{id}")
	public String eliminarZapatilla(@PathVariable(name = "id") int idZapatilla, RedirectAttributes attributes)
	{
		System.out.println("Zapatilla eliminada con Id: "  + idZapatilla);
		zapatillaService.eliminar(idZapatilla);
		attributes.addFlashAttribute("msg","Zapatilla Eliminada");
		
		return "redirect:/zapatilla/listar"; 
	}
	
	@ModelAttribute // Sirve para agregar al modelo los atributos que queramos, pero solo esta disponible para los metodos agregados en este controlador
	public void setGenericos(Model model) {
		model.addAttribute("zapatillas", zapatillaService.mostrar()); //zapatillaService.buscarZapatillasCateDescrip()
		model.addAttribute("categorias", categoriaService.mostrar());
		model.addAttribute("marcas", marcaService.mostrar());
	}
	
	
	
}
