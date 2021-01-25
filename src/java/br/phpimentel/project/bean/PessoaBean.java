package br.phpimentel.project.bean;

import br.phpimentel.project.model.PessoaModel;
import br.phpimentel.project.service.PessoaService;
import br.phpimentel.project.service.dao.PessoaDAO;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author phpimentel
 */
@SessionScoped
@ManagedBean
public class PessoaBean implements Serializable {
    
    private PessoaModel pessoaModel;
    
    private List<PessoaModel> listPessoaModel;
    
    private PessoaService pessoaService = new PessoaService();
    
    private LazyDataModel<PessoaModel> lazyModel;
    
    public void init() {
        this.lazyLoading();
    }
    
    
    public void lazyLoading() {
        this.lazyModel = new LazyDataModel() {

            @Override
            public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
                int totalRowCount = pessoaService.getRowCount();

                if (getRowCount() <= 0) {
                    lazyModel.setRowCount(totalRowCount);
                }
                lazyModel.setPageSize(pageSize);
                return pessoaService.listarPessoaFilter(first + 1, first + pageSize, sortField, SortOrder.ASCENDING.equals(sortOrder));
            }

        };

//        this.lazyModel = new LazyDataModel<PessoaModel>() {
//            private static final long serialVersionUID = 1L;
//
//            @Override
//            public List<PessoaModel> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
//                setRowCount(pessoaService.getRowCount());
//                return pessoaService.listarPessoaFilter(first + 1, first + pageSize, sortField, SortOrder.ASCENDING.equals(sortOrder));
//            }
//            
//            
//        };
    }
    
    public List<PessoaModel> listPessoa() {
        return this.listPessoaModel = this.pessoaService.listPessoa();
    }
    
    public void adicionarPessoa(PessoaModel pessoaModel) {
        new PessoaDAO().save(pessoaModel);
    }

    /* GETTERS AND SETTERS */

    public PessoaModel getPessoaModel() {
        return pessoaModel;
    }

    public void setPessoaModel(PessoaModel pessoaModel) {
        this.pessoaModel = pessoaModel;
    }

    public List<PessoaModel> getListPessoaModel() {
        return listPessoaModel;
    }

    public void setListPessoaModel(List<PessoaModel> listPessoaModel) {
        this.listPessoaModel = listPessoaModel;
    }

    public PessoaService getPessoaService() {
        return pessoaService;
    }

    public void setPessoaService(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    public LazyDataModel<PessoaModel> getLazyModel() {
        return lazyModel;
    }
}
