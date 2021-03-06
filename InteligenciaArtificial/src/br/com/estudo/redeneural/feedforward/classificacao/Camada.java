package br.com.estudo.redeneural.feedforward.classificacao;

import java.util.ArrayList;
import java.util.List;

import br.com.estudo.redeneural.base.Neuronio;
import br.com.estudo.redeneural.funcaoaprendizado.Treino;
import br.com.estudo.redeneural.funcaoativacao.FuncaoAtivacao;

public class Camada {
	private int numInput;// numero de entradas
	private int numNeuronios;// neuronios da camada
	private List<Neuronio> neuronios = new ArrayList<Neuronio>();
	private Treino treino;

	/*
	 * todas as saidas da camada, para cada neuronio pode ser acessado a saida
	 * individual no proprio neuronio
	 */
	double[] ySaida;
	/**
	 * proxima camada, por exemplo esta e a camada de entrada a proxima sera a
	 * de saida
	 */
	private Camada proxima = null;

	private final double taxaAprendizado;

	/**
	 * Rede Neural FeeForward
	 * 
	 * @param numInput
	 *            numero de entradas
	 * @param numNeuronios
	 *            numero de neuronios na camada oculta
	 * @param numOutput
	 *            numero de neuronios na camada de saida
	 */
	public Camada(int numInput, int numNeuronios, final double taxaAprendizado, FuncaoAtivacao funcao, Treino treino) {

		this.numInput = numInput;
		this.numNeuronios = numNeuronios;
		this.taxaAprendizado = taxaAprendizado;
		this.treino = treino;
		/*
		 * Inicializando cada camada, e interessante notar que para cada camada
		 * pode ser determinado a funcao de ativacao
		 */
		inicializaCamada(neuronios, numNeuronios, numInput, funcao, treino);
		ySaida = new double[numNeuronios];
	}

	/**
	 * Metodo responsavel por instanciar os neuronios de uma camada X
	 * 
	 * @param camada
	 *            camada que se deseja instanciar
	 * @param tamanho
	 *            total de neuronios da camada
	 * @param entradas
	 *            total de entradas da camada
	 */
	private void inicializaCamada(List<Neuronio> camada, int tamanho, int entradas, FuncaoAtivacao funcaoAtivacao,
			Treino treino) {
		for (int i = 0; i < tamanho; i++) {
			camada.add(new Neuronio(entradas, treino, funcaoAtivacao));
		}
	}

	private void inicializaCamada(List<Neuronio> camada, int tamanho, int entradas, Treino treino) {
		for (int i = 0; i < tamanho; i++) {
			camada.add(new Neuronio(entradas, treino));
		}
	}

	/**
	 * Metodo responsavel por classificar, ou estimular a rede
	 * 
	 * @param x
	 *            Percepcoes do agente enviadas para a rede neural
	 * 
	 * @return um valor de ativacao da camada de saia
	 */
	public double[] classifica(double[] x) {

		System.out.println("------------------------Pesos oculto ------------------");
		for (int i = 0; i < numNeuronios; i++) {
			for (int y = 0; y < neuronios.get(i).getW().length; y++) {
				System.out.println(neuronios.get(i).getW()[y]);
			}
		}

		for (int i = 0; i < numNeuronios; i++) {
			ySaida[i] = neuronios.get(i).classificar(x);
		}
		/*
		 * uma vez calculada a saida de todos sos neuronios desta camada o valor
		 * de saida e agora enviado para a proxima camada se ela existir e sua
		 * (a proxima camada) saida passa ser o retorno deste metodo, caso
		 * contrario serao os valores dos neuronios da camada atual.
		 */
		if (proxima != null) {
			return proxima.classifica(ySaida);
		}
		return ySaida;
	}

	public void update(Neuronio neuronio) {
		neuronios.add(neuronios.indexOf(neuronio), neuronio);
	}

	public boolean isOutput() {
		return (proxima == null ? true : false);
	}

	public Camada getProxima() {
		return proxima;
	}

	public void setProxima(Camada proxima) {
		this.proxima = proxima;
	}

	public List<Neuronio> getNeuronios() {
		return neuronios;
	}

	public void setNeuronios(List<Neuronio> neuronios) {
		this.neuronios = neuronios;
	}

	public Treino getTreino() {
		return treino;
	}

	public void setTreino(Treino treino) {
		this.treino = treino;
	}

	public void setPesos(double[][] w) {
		for (int i = 0; i < numNeuronios; i++) {
			for (int x = 0; x < numInput; x++) {
				neuronios.get(i).setW(x, w[i][x]);
			}
		}
	}

}
