package grafos;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Grafo {

	// grafos por matriz de adyacencia
	private Arista[][] A;
	private Set<Arista> E;

	// la cantidad de vertices esta predeterminada desde el constructor
	public Grafo(int vertices) {
		A = new Arista[vertices][vertices]; // all null, no aristas
		E = new TreeSet<Arista>();
	}

	// Constructor para Grafo Aleatorio con Aristas de peso aleatorio
	public Grafo(int vertices, int aristas) {

		int aristasMAX = (vertices * vertices - vertices) / 2;

		if (aristas > aristasMAX)
			throw new IllegalArgumentException(
					"El numero de aristas ingresadas no puede ser mayor a: " 
			+ aristasMAX + " para vertices = " + vertices);

		A = new Arista[vertices][vertices];
		E = new TreeSet<Arista>();
		Random random = new Random();
		int i = 0;
		
		while (i < aristas) {
			int verticeOrigen = random.nextInt(vertices);
			int verticeDestino = random.nextInt(vertices);

			if (verticeOrigen != verticeDestino && A[verticeOrigen][verticeDestino] == null) {
				this.agregarArista(verticeOrigen, verticeDestino);
				i++;
			}
		}
	}
	

	// Agrega arista con un peso double random entre 0 y 1
	public void agregarArista(int i, int j) {

		verificarVertice(i);
		verificarVertice(j);
		verificarLoop(i, j);

		Arista nueva = new Arista(i, j);
		A[i][j] = nueva;
		A[j][i] = nueva;
		E.add(nueva);
	}

	// Agrega arista con peso predefinido por parametro
	public void agregarAristaConPeso(int i, int j, double peso) {
		verificarVertice(i);
		verificarVertice(j);
		verificarLoop(i, j);

		Arista nueva = new Arista(i, j, peso);
		A[i][j] = A[j][i] = nueva;
		E.add(nueva);
	}

	public double pesoArista(int i, int j) {
		return A[i][j].getPeso();
	}

	private void verificarLoop(int i, int j) {
		if (i == j) {
			throw new IllegalArgumentException("No se permiten loops: (" + i + ", " + j + ")");
		}
	}

	// Verifica que sea un vertice v�lido
	private void verificarVertice(int i) {
		if (i < 0 || i >= A.length) {
			throw new IllegalArgumentException("El vertice no puede exceder los limites del grafo: " + i);
		}
	}

	public void eliminarArista(int i, int j) {
		verificarVertice(i);
		verificarVertice(j);
		verificarLoop(i, j);
		if (this.existeArista(i, j))
			E.remove(A[i][j]);
		A[i][j] = null;
		A[j][i] = null;

	}

	public int tamanio() {
		return A.length;
	}

	// Vecinos de un vertice
	public Set<Integer> vecinos(int i) {

		verificarVertice(i);
		Set<Integer> ret = new HashSet<Integer>();

		for (int j = 0; j < this.tamanio(); j++)
			if (i != j) {
				if (this.existeArista(i, j)) {
					ret.add(j);
				}
			}
		return ret;
	}

	public boolean existeArista(int i, int j) {
		verificarVertice(i);
		verificarVertice(j);
		verificarLoop(i, j);

		return A[i][j] != null;
	}

	public Set<Arista> getAristas() {
		return E;
	}

}
