package org.eclipse.aether.examples.microbackend;

import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.collection.CollectResult;
import org.eclipse.aether.examples.GetDependencyTree;
import org.eclipse.aether.examples.util.Booter;
import org.eclipse.aether.examples.util.ConsoleDependencyGraphDumper;
import org.eclipse.aether.graph.Dependency;

import static spark.Spark.get;

/**
 * Created by dror on 21/12/2016.
 */
public class Server {
    public static void main(String[] args) {
        System.out.println( "Setting up MicoBackend" );
        get("/hello", (req, res) -> "Hello World");
        get("/peek", (req, res) -> {
            String artifactCoords = req.queryParams("artifactcoords");
            if(artifactCoords != null) {
                getDependencyTree(artifactCoords);
                return "Working on coords: " + artifactCoords;
            } else {
                return "Missing parameter: artifactcoords";
            }
        });
    }

    public static void getDependencyTree( String artifactCoords )
            throws Exception
    {
        System.out.println( "------------------------------------------------------------" );
        System.out.println( GetDependencyTree.class.getSimpleName() );

        RepositorySystem system = Booter.newRepositorySystem();

        RepositorySystemSession session = Booter.newRepositorySystemSession( system );

        // e.g. "org.apache.maven:maven-aether-provider:3.1.0"
        Artifact artifact = new DefaultArtifact( artifactCoords );

        CollectRequest collectRequest = new CollectRequest();
        collectRequest.setRoot( new Dependency( artifact, "" ) );
        collectRequest.setRepositories( Booter.newRepositories( system, session ) );

        CollectResult collectResult = system.collectDependencies( session, collectRequest );

        collectResult.getRoot().accept( new ConsoleDependencyGraphDumper() );
    }
}
