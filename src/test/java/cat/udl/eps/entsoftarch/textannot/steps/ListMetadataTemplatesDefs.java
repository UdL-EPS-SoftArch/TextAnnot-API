package cat.udl.eps.entsoftarch.textannot.steps;

import cat.udl.eps.entsoftarch.textannot.domain.MetadataTemplate;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

//
//Tal i com ho faria jo:
//  1.- Saber amb qui interactuem perquè no sabem de qui hem de rebre la resposta, ni com es demana aquesta resposta.
//      Entenc que un requisit que necessitariem és que existeixi l'element que guarda i retorna les MetadataTemplate's,
//      per tant un requisit més que s'hauria d'afegir a la feature seria que existís aquest element, també.
//
//  2.- En el cas de la funció de la linia 10(la primera de la classe), si l'objecte en qüestió tingui també
//      un mètode per crear instàncies de MetadataTemplate, s'utilitzarà per crear-ne una i guardar-la.
//      En cas contrari, amb el mètode pertinent de l'objecte guardarem la instància de MetadataTemplate
//      creada.
//
//  3.- En el cas de la última funció de la classe, si l'objecte en qüestió te un mètode per crear
//      instàncies de MetadataTemplate, amb un bucle es creen i es guarden tantes instàncies com ens indiqui
//      l'argument del mètode que arrbi. En cas contrari, Es crea un array de MetadataTemplate amb tantes
//      posicions com indiqui l'argument, del mètode, que s'ha passat. Per cada posició es crea una
//      instància, i al sortir del bucle es guarden una a una amb l'objecte en qüestió.
//
//


public class ListMetadataTemplatesDefs {
    @And("^There is a metadata template with name \"([^\"]*)\"$")
    public void thereIsAMetadataTemplateWithName(String metadataTemplateName) throws Throwable {
        MetadataTemplate mt = new MetadataTemplate(metadataTemplateName);
        throw new PendingException();
    }

    @When("^I retrieve all metadata templates$")
    public void iRetrieveAllMetadataTemplates() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The respone contains a MetadataTemplate with name \"([^\"]*)\"$")
    public void theResponeContainsAMetadataTemplateWithName(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I retrieve all MetadataTemplate$")
    public void iRetrieveAllMetadataTemplate() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The response is a MetadataTemplate$")
    public void theResponseIsAMetadataTemplate() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^the response contains a MetadataTemplate with name \"([^\"]*)\"$")
    public void theResponseContainsAMetadataTemplateWithName(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^The response is a MetadataList$")
    public void theResponseIsAMetadataList() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^There are (\\d+) MetadataTemplates$")
    public void thereAreMetadataTemplates(int numOfMetadataTemplates) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
    }
}
