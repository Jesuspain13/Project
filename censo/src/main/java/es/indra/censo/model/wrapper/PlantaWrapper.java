package es.indra.censo.model.wrapper;

import java.util.ArrayList;
import java.util.List;

import es.indra.censo.model.Puesto;

/**
 * Nos permite ordenar correctamente los puestos de una planta, teniendo en
 * cuenta que hay puestos con id alfanumérico
 * 
 * @author jjespana
 *
 */
public class PlantaWrapper extends PlantaWrapperAbs {

	private int incioValoresAlfanumericos = 273;

	public PlantaWrapper() {
		super();
	}
	
	@Override
	public List<Puesto> ordenarPuesto(String nombrePlanta, List<Puesto> puestosDesordenados) throws NoSorteableException {
		try {
			return this.separarPuestos(puestosDesordenados);
		} catch (Exception ex) {
			throw new NoSorteableException(
					String.format("la lista de puestos de la planta %s no se ha podido ordenar", nombrePlanta));
		}
	}

//	@Override
//	public List<Puesto> ordenarPuesto(String nombrePlanta, List<Puesto> puestosDesordenados)
//			throws NoSorteableException {
//		try {
//
//			int value;
//			for (int i = 1; i < puestosDesordenados.size() - 18; i++) {
//				if (i > 1 && this.incioValoresAlfanumericos < 291) {
//					value = Integer.parseInt(puestosDesordenados.get(i - 1).getIdPuesto());
//					// si se cumple que la iteración actual es mayor que la anterior, entonces es
//					// numérico
//					if (value == 146 || value == 158 || value == 179 || value == 196 || value == 210 || value == 227
//							|| value == 244 || value == 261 || value == 278 || value == 295 || value == 306
//							|| value == 317 || value == 328 || value == 339 || value == 352 || value == 369
//							|| value == 386 || value == 403) {
//
//						this.añadirValoresAlfanumericos(puestosDesordenados, value, i);
//
//					} else {
//						super.addPuesto(puestosDesordenados.get(i));
//					}
//				} else {
//					super.addPuesto(puestosDesordenados.get(i));
//				}
//			}
//
//			return getPuestosOrdenados();
//
//		} catch (Exception ex) {
//			throw new NoSorteableException(
//					String.format("la lista de puestos de la planta %s no se ha podido ordenar", nombrePlanta));
//		}
//	}
//
//	private void añadirValoresAlfanumericos(List<Puesto> puestosDesordenados, int value, int i) {
//		switch (value) {
//		case 146:
//			break;
//		case 158:
//			break;
//		case 179:
//			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(i));
//			break;
//		case 196:
//			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(i));
//			break;
//		case 210:
//			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(i));
//			break;
//		case 227:
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(i));
//			break;
//		case 244:
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(i));
//			break;
//		case 261:
//			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(i));
//			break;
//		case 278:
//			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(i));
//			break;
//		case 295:
//			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(i));
//			break;
//		case 306:
//			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(i));
//			break;
//		case 317:
//			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(i));
//			break;
//		case 328:
//			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(i));
//			break;
//		case 339:
//			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(i));
//			break;
//		case 352:
//			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(i));
//			break;
//		case 369:
//			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(i));
//			break;
//		case 386:
//			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(i));
//			break;
//		case 403:
//			super.addPuesto(puestosDesordenados.get(this.incioValoresAlfanumericos));
//			this.incioValoresAlfanumericos++;
//			super.addPuesto(puestosDesordenados.get(i));
//			break;
//		}
//	}
	
	// NUEVO ALGORITMO DE ORDENACION
		private List<Puesto> puestosNormales = new ArrayList<Puesto>();
		private List<Puesto> puestosConA = new ArrayList<Puesto>();
		
		
		
		/**
		 * Recorre la lista de puestos y los ordena
		 * @param puestosPlanta
		 * @return
		 */
		public List<Puesto> separarPuestos(List<Puesto> puestosPlanta) {
			String idPuesto = null;
			for (Puesto p: puestosPlanta) {
				
				//comprobamos si tiene Z/z o A/a o es numerico y lo añadimos a su lista
				this.decidirTipoPuesto(idPuesto, p);
				
			}
			//una vez separados los tipos de puestos -> introducir los A en los normales
			this.introducirAlfanumericosAMostrarEnNormales("A");
			return puestosNormales;
		}
		
		/**
		 * Recibe el nombre del puesto y comprueba si contiene Z / A o es normal(numerico)
		 * @param idPuesto
		 */
		private void decidirTipoPuesto(String idPuesto, Puesto p) {
			//nombre del puesto en cada iteración
			idPuesto = p.getIdPuesto();
			if(idPuesto.contains("147") || idPuesto.contains("159") || idPuesto.contains("213A") || idPuesto.contains("230A") ) {
				//no se los salta
			} else if (idPuesto.contains("A") || idPuesto.contains("a")) {
				puestosConA.add(p);
			} else if(idPuesto.contains("D") || idPuesto.contains("d")) {
				
			} else {
				puestosNormales.add(p);
			}
		}
		
		/**
		 * recorre los puestos con A, compara el id de ese puesto sin la letra con el id del puesto normal.
		 * Si coinciden, es que el puesto con A va delante de él
		 * @param letraDeAlfanumericos
		 */
		private void introducirAlfanumericosAMostrarEnNormales(String letraDeAlfanumericos) {
			boolean introducido;
			int iteracionPuestoSinLetra;
			int iteracionPuestoNormal;
			String sinLetra;
			int contador = 0;
			for (Puesto p: puestosConA) {
				sinLetra = p.getIdPuesto().replace(letraDeAlfanumericos, "");
				iteracionPuestoSinLetra = Integer.parseInt(sinLetra);
				introducido = false;
				while(!introducido) {
					if (!puestosNormales.get(contador).getIdPuesto().contains("A")) {
						iteracionPuestoNormal = Integer.parseInt(puestosNormales.get(contador).getIdPuesto());
						if (iteracionPuestoSinLetra == iteracionPuestoNormal) {
							
							int indiceDondeIntroducir = contador+1;
							puestosNormales.add(indiceDondeIntroducir, p);
							introducido = true;
						}
					}
					contador++;
				}
			}
		}

}
