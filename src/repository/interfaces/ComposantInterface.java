package repository.interfaces;

import domaine.Composant;
import domaine.MainDouvre;
import domaine.Material;

import java.util.List;

public interface ComposantInterface {
    void addComposant(Composant composant, int project_id) throws Exception;
    List<Material> getAllMaterialByProjectName(String projectName);
    List<MainDouvre> getAllMainDouvreByProjectName(String projectName);

    void addMaterial(Material material, int project_id) throws Exception;
    void addMainDouvre(MainDouvre mainDouvre, int project_id) throws Exception;
}
