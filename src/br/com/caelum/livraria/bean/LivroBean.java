package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@ManagedBean
@ViewScoped
public class LivroBean {

	private Livro livro = new Livro();

	private Integer autorId;
	
	private List<Livro> livros;
	
	public Livro getLivro() {
		return livro;
	}

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public void associarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
		
		this.livro.adicionaAutor(autor);
	}
	
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("O autor é obrigatório"));
			return;
		}

		new DAO<Livro>(Livro.class).adiciona(this.livro);
		this.livro = new Livro();
		this.livros = null;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public List<Livro> getLivros() {
		if (livros == null) {
			livros = new DAO<Livro>(Livro.class).listaTodos();
		}
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object object) {
		String isbn = object.toString();
		if (!isbn.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("Deveria começar com 1"));
		}
	}
}
