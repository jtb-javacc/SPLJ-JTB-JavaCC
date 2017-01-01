package spl;

import spl.syntaxtree.CompilationUnit;
import spl.visitor.TreeDumper;

public class SPL {
	/**
	 * Returns the root node of the AST. It only makes sense to call this after
	 * a successful parse.
	 * 
	 * @return the root node
	 */
	public static void main(String[] args) {
		Parser parser;
		if (args.length == 1) {
			System.out.println(
					"Simple Programming Language Interpreter:  Reading from file " + args[0] + " . . .");
			try {
				parser = new Parser(new java.io.FileInputStream(args[0]));
			} catch (java.io.FileNotFoundException e) {
				System.out.println(
						"Simple Programming Language Interpreter:  File " + args[0] + " not found.");
				return;
			}
		} else {
			System.out.println("Simple Programming Language Interpreter:  Usage :");
			System.out.println("         java SPL inputfile");
			return;
		}
		try {
			CompilationUnit compilationUnit;
			compilationUnit = parser.CompilationUnit();
			Interpret interpret = new Interpret();
			interpret.visit(compilationUnit);
			// TreeFormatter treeFormatter = new TreeFormatter();
			// treeFormatter.visit(compilationUnit);
			TreeDumper treeDumper = new TreeDumper();
			treeDumper.visit(compilationUnit);
		} catch (ParseException e) {
			System.out
					.println("Simple Programming Language Interpreter:  Encountered errors during parse.");
			e.printStackTrace();
		} catch (Exception e1) {
			System.out.println(
					"Simple Programming Language Interpreter:  Encountered errors during interpretation/tree building.");
			e1.printStackTrace();
		}
	}

}
