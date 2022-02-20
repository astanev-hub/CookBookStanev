package br.com.stanev.cookbook.view;

import java.util.Locale;

import br.com.stanev.cookbook.domain.Ingrediente;
import br.com.stanev.cookbook.domain.Receita;
import br.com.stanev.cookbook.domain.Rendimento;
import br.com.stanev.cookbook.enums.Categoria;
import br.com.stanev.cookbook.enums.TipoMedida;
import br.com.stanev.cookbook.enums.TipoRendimento;

public class EditReceitaView {
    private Receita receita;

    public EditReceitaView(Receita receita, int origem) {
    	if(origem == 1) {
    		this.receita = new Receita(receita);
    	} else {
            this.receita = new Receita(receita.getNome(), receita.getCategoria());    		
    	}
    }

    public Receita edit() {
        boolean confirm = false;
        boolean fim = true;
        String salva = "";
        
        do {
        	salva = showMenu();
            new ReceitaView(receita).fullView(System.out);
            if (salva == "S" || salva == "X") {
            	fim = false;
            	if (salva == "S") confirm = true;
            }
        } while(fim);
     
        if (confirm) {
            return receita;   
        } else {
            return null;
        }
    }

    private String showMenu() {
        String[] options = new String[8];
        StringBuilder sb = new StringBuilder("#".repeat(100));

        sb.append("%n").append("  N : Nome %n");
        options[0] = "N";
        sb.append("  C : Categoria %n");
        options[1] = "C";
        sb.append("  T : Tempo de Preparo %n");
        options[2] = "T";
        sb.append("  R : Rendimento %n");
        options[3] = "R";
        sb.append("  I : Ingredientes %n");
        options[4] = "I";
        sb.append("  P : Preparos %n");
        options[5] = "P";

        sb.append("  # ").append("# ".repeat(48)).append("%n");
        sb.append("  X : Sair sem salvar a receita%n");
        options[6] = "X";

        sb.append("  S : Sair salvando a receita %n");
        options[7] = "S";
        
        sb.append("#".repeat(100)).append("%n");

        String opcao = ConsoleUtils.getUserOption(sb.toString(), options).toUpperCase(Locale.getDefault());
        switch (opcao) {
            case "N":
                nome();
                break;
            case "C":
            	categoria();
            	break;
            case "T":
            	tempoPreparo();
            	break;
            case "R":
                rendimento();
                break;
            case "I":
                ingrediente();
                break;
            case "P":
                preparo();
                break;
            case "X":
                System.out.println("Ok, saindo sem salvar!");
                return "X";         
            case "S":
                System.out.println("Ok, salvando e saindo!");
                return "S";         
            default:
                System.out.println("Opção Inválida!!!");
        }
        return "";
    }
    
    private boolean showMenuIngredientes() {
        String[] options = new String[3];
        StringBuilder sb = new StringBuilder("#".repeat(100));

        sb.append("%n").append("  + : Adicionar Ingrediente %n");
        options[0] = "+";

        if (receita.getIngredientes().size() != 0) {
            sb.append("  - : Remover  Ingrediente %n");
            options[1] = "-";
        }

        sb.append("  # ").append("# ".repeat(48)).append("%n");
        sb.append("  X : Sair dos Ingredientes %n");
        options[2] = "X";
        sb.append("#".repeat(100)).append("%n");

        String opcao = ConsoleUtils.getUserOption(sb.toString(), options).toUpperCase(Locale.getDefault());
        switch (opcao) {
            case "+":
                addIngrediente();
                break;
            case "-":
                delIngrediente();
                break;
            case "X":
                System.out.println("Ok, saindo dos Ingredientes!!");
                return false;
            default:
                System.out.println("Opção Inválida!!!");
        }
        return true;
    }
     
    private boolean showMenuPreparos() {
        String[] options = new String[3];
        StringBuilder sb = new StringBuilder("#".repeat(100));

        sb.append("%n").append("  + : Adicionar Preparo %n");
        options[0] = "+";

        if (receita.getPreparo().size() != 0) {
            sb.append("  - : Remover Preparo %n");
            options[1] = "-";
        }

        sb.append("  # ").append("# ".repeat(48)).append("%n");
        sb.append("  X : Sair do Preparo %n");
        options[2] = "X";
        sb.append("#".repeat(100)).append("%n");

        String opcao = ConsoleUtils.getUserOption(sb.toString(), options).toUpperCase(Locale.getDefault());
        switch (opcao) {
            case "+":
                addPreparo();
                break;
            case "-":
                delPreparo();
                break;
            case "X":
                System.out.println("Ok, saindo do Preparo!");
                return false;
            default:
                System.out.println("Opção Inválida!!!");
        }
        return true;
    }
    
    private void preparo() {
    	do {
    		new ReceitaView(receita).fullView(System.out);
    	} while (showMenuPreparos());
    }

	private void ingrediente() {
    	do {
    		new ReceitaView(receita).fullView(System.out);
    	} while (showMenuIngredientes());
	}
	
	private void addIngrediente() {
	
		String nome = ConsoleUtils.getUserInput("Qual o nome do Ingrediente?");
	    Double quantidade = ConsoleUtils.getUserDouble("Qual a quantidade?");
		
		StringBuilder sb = new StringBuilder("Qual o tipo de medida do Ingrediente?\n");
        String[] options = new String[TipoMedida.values().length];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i);
            sb.append(String.format("%d - %s%n", i, TipoMedida.values()[i]));
        }
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
        TipoMedida tipoMedida = null;
        for (int i = 0; i < options.length; i++) {
            if (opcao.equalsIgnoreCase(options[i])) {
                tipoMedida = TipoMedida.values()[i];
                break;
            }
        }
        
        Ingrediente ingrediente = new Ingrediente(nome, quantidade, tipoMedida);
        receita.setIngrediente(ingrediente);

	}
	
	private void delIngrediente() {
		StringBuilder sb = new StringBuilder("Qual ingrediente você quer excluir?\n");
        String[] options = new String[receita.getIngredientes().size()];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i);
            sb.append(String.format("%d - %s%n", i, receita.getIngredientes().get(i).getNome()));
        }
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
        for (int i = 0; i < options.length; i++) {
            if (opcao.equalsIgnoreCase(options[i])) {
                receita.delIngrediente(i);
                break;
            }
        }
	}
	
	private void addPreparo() {
		
		String preparo = ConsoleUtils.getUserInput("Digite o preparo");

		if (receita.getPreparo().size() == 0) {
			receita.setPreparo(preparo);
		} else {
			StringBuilder sb = new StringBuilder("Em qual posiçao deseja incluir o preparo?\n");
	        String[] options = new String[receita.getPreparo().size() + 1];
	        for (int i = 0; i < options.length-1; i++) {
	            options[i] = String.valueOf(i);
	            sb.append(String.format("%d - %s%n", i, receita.getPreparo().get(i)));
	        }
            sb.append(String.format("%d - %s%n",receita.getPreparo().size(),""));
	        options[receita.getPreparo().size()] = String.valueOf(receita.getPreparo().size());
	        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
	        
	        receita.setPreparoLocal(preparo, Integer.parseInt(opcao));
		}			
	}
	
	private void delPreparo() {
		StringBuilder sb = new StringBuilder("Qual preparo você quer excluir?\n");
        String[] options = new String[receita.getPreparo().size()];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i);
            sb.append(String.format("%d - %s%n", i, receita.getPreparo().get(i)));
        }
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
        for (int i = 0; i < options.length; i++) {
            if (opcao.equalsIgnoreCase(options[i])) {
                receita.delPreparo(i);
                break;
            }
        }
	}
	
	private void rendimento() {
        StringBuilder sb = new StringBuilder("Qual o tipo de Rendimento?\n");
        String[] options = new String[TipoRendimento.values().length];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i);
            sb.append(String.format("%d - %s%n", i, TipoRendimento.values()[i]));
        }
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
        TipoRendimento tipoRendimento = null;
        for (int i = 0; i < options.length; i++) {
            if (opcao.equalsIgnoreCase(options[i])) {
                tipoRendimento = TipoRendimento.values()[i];
                break;
            }
        }
        int rendimentoMin = ConsoleUtils.getUserInt("Qual o rendimento mínimo da Receita?");
        int rendimentoMax = ConsoleUtils.getUserInt("Qual o rendimento máximo da Receita?");
        Rendimento rendimento = new Rendimento(rendimentoMin, rendimentoMax, tipoRendimento);
        receita.setRendimento(rendimento);
	}

	private void tempoPreparo() {
        double tempo = ConsoleUtils.getUserDouble("Qual o tempo de preparo, em minutos?");
        receita.setTempoPreparo(tempo);		
	}
	
	private void nome() {
        String name = ConsoleUtils.getUserInput("Qual o novo nome da receita?");
        if (!name.isBlank()) {
        	receita.setNome(name);
        }
	}

	private void categoria() {
        StringBuilder sb = new StringBuilder("Qual a nova categoria da receita?\n");
        String[] options = new String[Categoria.values().length];
        for (int i = 0; i < options.length; i++) {
            options[i] = String.valueOf(i);
            sb.append(String.format("%d - %s%n", i, Categoria.values()[i]));
        }
        String opcao = ConsoleUtils.getUserOption(sb.toString(), options);
        Categoria categoria = null;
        for (int i = 0; i < options.length; i++) {
            if (opcao.equalsIgnoreCase(options[i])) {
                categoria = Categoria.values()[i];
                break;
            }
        }
        receita.setCategoria(categoria);
	}
	
}
