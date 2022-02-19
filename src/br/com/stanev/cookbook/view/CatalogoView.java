package br.com.stanev.cookbook.view;

import br.com.stanev.cookbook.controller.Catalogo;
import br.com.stanev.cookbook.domain.Receita;
import br.com.stanev.cookbook.enums.Categoria;

import java.util.Locale;

public class CatalogoView {
    private Catalogo controller;
    private Receita ative;
    private int currentIndex;

    public CatalogoView(Catalogo controller) {
        this.controller = controller;
        if (controller.getTotal() > 0) {
            currentIndex = 1;
            ative = controller.getReceita(currentIndex);
        } else {
            currentIndex = 0;
            ative = null;
        }
    }

    private boolean showMenu() {
        String[] options = new String[7];
        StringBuilder sb = new StringBuilder("#".repeat(100));

        sb.append("%n").append("  + : Adicionar  %n");
        options[0] = "+";

        if (ative != null) {
            sb.append("  E : Editar  %n");
            options[1] = "E";
            sb.append("  - : Remover  %n");
            options[2] = "-";
        }

        if (controller.getTotal() > 1) {
            sb.append("  P : Próxima  %n");
            options[3] = "P";
            sb.append("  A : Anterior  %n");
            options[4] = "A";
            sb.append("  L : Localizar  %n");
            options[5] = "L";
        }

        sb.append("  # ").append("# ".repeat(48)).append("%n");
        sb.append("  X : Sair  %n");
        options[6] = "X";
        sb.append("#".repeat(100)).append("%n");

        String opcao = ConsoleUtils.getUserOption(sb.toString(), options).toUpperCase(Locale.getDefault());
        switch (opcao) {
            case "+":
                add();
                break;
            case "-":
                del();
                break;
            case "P":
                next();
                break;
            case "A":
                previous();
                break;
            case "L":
                find();
                break;
            case "E":
            	edit();
            	break;
            case "X":
                System.out.println("Obrigado!!");
                return false;
            default:
                System.out.println("Op��o Inv�lida!!!");
        }
        return true;
    }

    private void find() {
        String name = ConsoleUtils.getUserInput("Qual o nome da receita que deseja localizar?");
        ative = controller.getReceita(name);
        currentIndex = 0;
    }

    private void next() {
        if (ative != null) currentIndex++;
        try {
            ative = controller.getReceita(currentIndex);
        } catch (IllegalArgumentException e) {
            ative = null;
        }
        if (ative == null) {
            currentIndex = 1;
            ative = controller.getReceita(currentIndex);
        }
    }

    private void previous() {
        if (ative != null) currentIndex--;
        try {
            ative = controller.getReceita(currentIndex);
        } catch (IllegalArgumentException e) {
            ative = null;
        }
        if (ative == null) {
            currentIndex = controller.getTotal();
            ative = controller.getReceita(currentIndex);
        }
    }

    private void del() {
        String opcao = ConsoleUtils.getUserOption("Você deseja realmente APAGAR a receita " + ative.getNome() + "?\nS - Sim   N - Não", "S", "N");
        if (opcao.equalsIgnoreCase("S")) {
            controller.del(ative.getNome());
            ative = null;
            if (controller.getTotal() > 0) {
                currentIndex = 0;
                next();
            }
        }
    }

    private void edit() {
    	new ReceitaView(ative).fullView(System.out);
        Receita nova = new EditReceitaView(ative, 1).edit();
        if (nova != null) {
            controller.del(ative.getNome());
            controller.add(nova);
            ative = nova;
            currentIndex = 0;
        }
    }

    private void add() {
        String name = ConsoleUtils.getUserInput("Qual o nome da nova receita?");
        if (!name.isBlank()) {
            Receita other = controller.getReceita(name);
            if (other != null) {
                String opcao = ConsoleUtils.getUserOption("Receita já existente!%nVocê deseja visualizar?%nS - Sim   N - Não", "S", "N");
                if (opcao.equalsIgnoreCase("S")) {
                    ative = other;
                }
            } else {
                StringBuilder sb = new StringBuilder("Qual a categoria da nova receita?\n");
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
                Receita nova = new EditReceitaView(new Receita(name, categoria),2).edit();
                if (nova != null) {
                    controller.add(nova);
                    ative = nova;
                    currentIndex = 0;
                }
            }
        }
    }

    public void view() {
        do {
            new ReceitaView(ative).fullView(System.out);
        } while (showMenu());
    }
}
