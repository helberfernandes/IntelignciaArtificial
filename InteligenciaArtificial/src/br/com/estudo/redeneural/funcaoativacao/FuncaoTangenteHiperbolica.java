package br.com.estudo.redeneural.funcaoativacao;

import br.com.estudo.redeneural.exception.NeuralException;

public class FuncaoTangenteHiperbolica implements FuncaoAtivacao {

	/**
	 * Embora haja excecoes, em geral, a funcaoo tangente hiperbolica � a melhor
	 * op��o para a ativa��o da camada oculta.
	 */
	@Override
	public double funcaoAtivacao(double n) {
//		if (n < -20.0)
//			return -1.0;
//		else if (n > 20.0)
//			return 1.0;
//		else
			return Math.tanh(n);
	}

	@Override
	public double funcaoDerivada(double n) {
		return 1-Math.pow(n, 2);
	}

	@Override
	public double[] funcaoAtivacao(double[] oSums) {
		throw new NeuralException("Funcao nao implementada");
	}

}
