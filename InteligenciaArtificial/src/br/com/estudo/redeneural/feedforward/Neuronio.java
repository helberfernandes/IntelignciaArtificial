package br.com.estudo.redeneural.feedforward;

import java.util.Arrays;

import br.com.estudo.redeneural.funcaoativacao.FuncaoAtivacao;
import br.com.estudo.redeneural.funcaoativacao.FuncaoLinear;
import br.com.estudo.redeneural.funcaoativacao.FuncaoSigmoid;

/**
 * Um neuronio recebe um conjunto de entradas e responde a estimolos
 * 
 * @author Helber Fernandes
 *
 */
public class Neuronio {
	private double[] x;// entradas
	private double[] w;// pesos
	private int numEntradas;
	private double bias = -1;
	private FuncaoAtivacao funcaoAtivacao;

	/**
	 * 
	 * @param numEntradas,
	 *            total de entradas para o neuronio
	 */
	public Neuronio(int numEntradas,FuncaoAtivacao funcaoAtivacao) {
		this.numEntradas = numEntradas;
		x = new double[numEntradas];
		w = new double[numEntradas];
		this.funcaoAtivacao = funcaoAtivacao;
	}
	

	public Neuronio(int numEntradas) {
		this(numEntradas, new FuncaoSigmoid());
	}


	/**
	 * Funcao que responsavel por classificar determinado problema
	 * 
	 * @param x
	 *            Percepcoes do agente enviadas para a rede neural
	 * @param usarFuncao
	 *            para este probleam especifico, a camada de saida usa uma
	 *            funcao que e ativada pela soma de todas as camadas de saida
	 *            para este caso passar false.
	 * @return resultado do processamenteo do neuronio
	 */
	public double classificar(double[] x) {
		double soma = 0.0;
		for (int i = 0; i < numEntradas; i++) {
			soma += x[i] * w[i];
		}
		soma += bias;
		
		return this.funcaoAtivacao.funcaoAtivacao(soma);

	}

	/**
	 * 
	 * @return pessos do neuronio
	 */
	public double[] getW() {
		return w;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	/**
	 * Para a ativa��o da camada de sa�da, se sua rede neural est� executando a
	 * classifica��o onde a vari�vel dependente a ser predita tem tr�s ou mais
	 * valores (por exemplo, predizer a inclina��o pol�tica de uma pessoa que
	 * pode ser "liberal", "moderada" ou "conservadora"), Softmax ativa��o � a
	 * melhor escolha.
	 */
	public static double[] funcaoDeAtivacaoSoftmax(double[] oSums) {
		// Does all output nodes at once.
		// Determine max oSum.
		double max = oSums[0];
		for (int i = 0; i < oSums.length; ++i)
			if (oSums[i] > max)
				max = oSums[i];

		// Determine scaling factor -- sum of exp(each val - max).
		double scale = 0.0;
		for (int i = 0; i < oSums.length; ++i)
			scale += Math.exp(oSums[i] - max);

		double[] result = new double[oSums.length];
		for (int i = 0; i < oSums.length; ++i)
			result[i] = Math.exp(oSums[i] - max) / scale;

		return result; // Now scaled so that xi sums to 1.0.
	}

}