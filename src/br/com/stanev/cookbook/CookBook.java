package br.com.stanev.cookbook;

import br.com.stanev.cookbook.controller.Catalogo;
import br.com.stanev.cookbook.domain.Receita;
import br.com.stanev.cookbook.enums.Categoria;
import br.com.stanev.cookbook.view.CatalogoView;

public class CookBook {
    public static void main(String[] args) {
        Catalogo catalogo = new Catalogo();
        catalogo.add(new Receita("Cookies da Lara 1", Categoria.DOCE));
        catalogo.add(new Receita("Cookies da Lara 2", Categoria.DOCE));
        catalogo.add(new Receita("Cookies da Lara 3", Categoria.DOCE));
        catalogo.add(new Receita("Cookies da Lara 4", Categoria.DOCE));
        CatalogoView view = new CatalogoView(catalogo);
        view.view();
    }
}
