
package com.jfixby.util.patch18.red;

import com.jfixby.r3.ext.api.patch18.P18Palette;
import com.jfixby.r3.ext.api.patch18.palette.Fabric;
import com.jfixby.r3.ext.api.patch18.palette.FabricsList;
import com.jfixby.r3.ext.api.patch18.palette.FabricsRelation;
import com.jfixby.r3.ext.api.patch18.palette.RelationsList;
import com.jfixby.scarabei.api.graphs.Edge;
import com.jfixby.scarabei.api.graphs.Graphs;
import com.jfixby.scarabei.api.graphs.MultiGraph;
import com.jfixby.scarabei.api.graphs.PathInGraph;
import com.jfixby.scarabei.api.graphs.PathState;
import com.jfixby.scarabei.api.graphs.Vertex;
import com.jfixby.scarabei.api.log.L;

class PaletteImpl implements P18Palette {
	final private String name;
	final private FabricsListImpl fabrics = new FabricsListImpl();
	final private FabricsRelationListImpl relations = new FabricsRelationListImpl();

	private MultiGraph<Fabric, FabricsRelation> fabric_relations_graph;

	public PaletteImpl (PaletteSpecsImpl palette_specs) {
		this.name = palette_specs.getName();
		fabrics.setup(palette_specs.getFabricsList());
		relations.setup(palette_specs.getRelationsList());

		fabric_relations_graph = Graphs.newUndirectedGraph();

		for (int i = 0; i < fabrics.size(); i++) {
			Fabric fabric_i = fabrics.getElementAt(i);
			Vertex<Fabric> node = fabric_relations_graph.newVertex();
			node.putVertexObject(fabric_i);
		}

		for (int i = 0; i < relations.size(); i++) {
			FabricsRelation relation = relations.getElementAt(i);
			Fabric lower_fabric = relation.getLowerFabric();
			Fabric upper_fabric = relation.getUpperFabric();

			Vertex<Fabric> vertex_a = fabric_relations_graph.findVertexByObject(lower_fabric);
			Vertex<Fabric> vertex_b = fabric_relations_graph.findVertexByObject(upper_fabric);

			Edge<FabricsRelation> edge = fabric_relations_graph.newEdge(vertex_a, vertex_b);

			edge.putObject(relation);

			// relation.get

		}

		// fabric_relations_graph.print("graph");
		// RedTriplane.exit();

	}

	@Override
	public void print () {
		L.d("Palette[" + name + "]");
		L.d("  fabrics:");
		L.d("    ", fabrics);
		L.d("  relations:");
		L.d("    ", relations);

	}

	@Override
	public FabricsList listFabrics () {
		return fabrics;
	}

	@Override
	public RelationsList listRelations () {
		return this.relations;
	}

	@Override
	public Fabric findClosestFabric (Fabric from_fabric, Fabric direction) {
		Vertex<Fabric> from_vertex = fabric_relations_graph.findVertexByObject(from_fabric);
		Vertex<Fabric> to_vertex = fabric_relations_graph.findVertexByObject(direction);

		PathInGraph<Fabric, FabricsRelation> path = fabric_relations_graph.findPath(from_vertex, to_vertex);

		if (path.numberOfSteps() > 0) {

			PathState<Fabric, FabricsRelation> state_1 = path.getState(1);

			Vertex<Fabric> next_vertex = state_1.getVertex();
			return next_vertex.getVertexObject();
		} else {
			return to_vertex.getVertexObject();
		}

	}

}
