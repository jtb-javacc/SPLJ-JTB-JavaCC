package spl;

import java.util.Stack;

import spl.syntaxtree.AdditiveExpression;
import spl.syntaxtree.INode;
import spl.syntaxtree.MultiplicativeExpression;
import spl.syntaxtree.NodeChoice;
import spl.syntaxtree.NodeListOptional;
import spl.syntaxtree.NodeSequence;
import spl.syntaxtree.ReadStatement;
import spl.syntaxtree.WriteStatement;
import spl.visitor.DepthFirstVoidVisitor;

public class Interpret extends DepthFirstVoidVisitor {
	private final Stack<Object> stack;

	{
		stack = new Stack<Object>();
	}

	/**
	 * Visits a {@link AdditiveExpression} node, whose children are the
	 * following :
	 * <p>
	 * f0 -> MultiplicativeExpression()<br>
	 * f1 -> ( %0 #0 "+" #1 MultiplicativeExpression()<br>
	 * .. .. | %1 #0 "-" #1 MultiplicativeExpression() )*<br>
	 *
	 * @param n
	 *            - the node to visit
	 */
	@Override
	public void visit(final AdditiveExpression n) {
		// f0 -> MultiplicativeExpression()
		final MultiplicativeExpression n0 = n.f0;
		n0.accept(this);
		// f1 -> ( %0 #0 "+" #1 MultiplicativeExpression()
		// .. .. | %1 #0 "-" #1 MultiplicativeExpression() )*
		final NodeListOptional n1 = n.f1;
		final NodeListOptional n1T = (NodeListOptional) n1;
		if (n1T.present()) {
			for (int i = 0; i < n1T.size(); i++) {
				final INode n1TMi = n1T.elementAt(i);
				final NodeChoice n1TMiC = (NodeChoice) n1TMi;
				final INode n1TMiCH = n1TMiC.choice;
				switch (n1TMiC.which) {
				case 0:
					// %0 #0 "+" #1 MultiplicativeExpression()
					final NodeSequence n1TMiCHS0 = (NodeSequence) n1TMiCH;
					// #0 "+"
					final INode n1TMiCHS00A0 = ((NodeSequence) n1TMiCHS0).elementAt(0);
					n1TMiCHS00A0.accept(this);
					// #1 MultiplicativeExpression()
					final INode n1TMiCHS00A1 = ((NodeSequence) n1TMiCHS0).elementAt(1);
					n1TMiCHS00A1.accept(this);
					break;
				case 1:
					// %1 #0 "-" #1 MultiplicativeExpression()
					final NodeSequence n1TMiCHS1 = (NodeSequence) n1TMiCH;
					// #0 "-"
					final INode n1TMiCHS11A0 = ((NodeSequence) n1TMiCHS1).elementAt(0);
					n1TMiCHS11A0.accept(this);
					// #1 MultiplicativeExpression()
					final INode n1TMiCHS11A1 = ((NodeSequence) n1TMiCHS1).elementAt(1);
					n1TMiCHS11A1.accept(this);
					break;
				default:
					// should not occur !!!
					break;
				}
			}
		}
	}

	@Override
	public void visit(final ReadStatement n) {
		// f0 -> "read"
		n.f0.accept(this);
		System.out.print("read ");
		// f1 -> <IDENTIFIER>
		n.f1.accept(this);
		System.out.println("identifier");
	}

	@Override
	public void visit(final WriteStatement n) {
		// f0 -> "write"
		n.f0.accept(this);
		System.out.print("write ");
		// f1 -> <IDENTIFIER>
		n.f1.accept(this);
		System.out.println("identifier");
	}
}
