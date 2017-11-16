package aula;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.Ontology;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.XSD;

public class Ontologia {

	public static void main(String args[]) {

		//criando um modelo de ontologia vazio
		OntModel ontModel = ModelFactory.createOntologyModel();
		String ns = "http://www.exemplo.com/onto1#";
		String uri = new String("http://www.exemplo.com/onto1");
		Ontology onto = ontModel.createOntology(uri);

		//criando as classes
		OntClass pessoa = ontModel.createClass(ns + "Pessoa");
		OntClass masculino = ontModel.createClass(ns + "Masculino");
		OntClass feminino = ontModel.createClass(ns + "Feminino");
		
		//defininindo subclasses
		pessoa.addSubClass(masculino);	
		pessoa.addSubClass(feminino);

		//disjun��o
		masculino.addDisjointWith(feminino);
		feminino.addDisjointWith(masculino);

		//propriedade de tipo de dado + dom�nio + range
		DatatypeProperty temIdade = ontModel.createDatatypeProperty(ns + "temIdade");
		temIdade.setDomain(pessoa);
		temIdade.setRange(XSD.integer);
		
		//individuals
		Individual joao = masculino.createIndividual(ns + "Jo�o");
		Individual maria = feminino.createIndividual(ns + "Maria");
		Individual jose = masculino.createIndividual(ns + "Jos�");
		
		//literal
		Literal idade20 = ontModel.createTypedLiteral("20", XSDDatatype.XSDint);
		
		//senten�a
		Statement joaoTem20 = ontModel.createStatement(joao,  temIdade, idade20);
		
		ontModel.add(joaoTem20);
		
		//propriedade de objeto + dom�nio + range
		ObjectProperty temIrmao = ontModel.createObjectProperty(ns + "temIrmao");
		temIrmao.setDomain(pessoa);
		temIrmao.setRange(pessoa);
		
		//senten�as
		Statement joaoIrmaoMaria = ontModel.createStatement(joao, temIrmao, maria);
		Statement mariaIrmaoJoao = ontModel.createStatement(maria, temIrmao, joao);
		
		ontModel.add(joaoIrmaoMaria);
		ontModel.add(mariaIrmaoJoao);
		
		String fileName = "Onto1.xml";
		FileWriter out;
		try {
			out = new FileWriter(fileName);
			ontModel.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
}