import java.util.Stack;

import syntaxtree.ReadStatement;
import syntaxtree.WriteStatement;
import visitor.DepthFirstVoidVisitor;

public class Interpret extends DepthFirstVoidVisitor {
	private Stack<Integer> stack;

	{
		stack = new Stack<Integer>();
	}

	public void visit(final ReadStatement n) {
		// f0 -> "read"
		n.f0.accept(this);
		System.out.print("read ");
		// f1 -> <IDENTIFIER>
		n.f1.accept(this);
		System.out.println("identifier");
	}

	public void visit(final WriteStatement n) {
		// f0 -> "write"
		n.f0.accept(this);
		System.out.print("write ");
		// f1 -> <IDENTIFIER>
		n.f1.accept(this);
		System.out.println("identifier");
	}
}
