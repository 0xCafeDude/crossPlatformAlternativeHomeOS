all: confirmation.pdf paper.pdf

confirmation.pdf: confirmation.tex
	pdflatex confirmation
	pdflatex confirmation
	pdflatex confirmation

paper.pdf: biblio.bib paper.tex abstract.tex intro.tex related.tex arch.tex implementation.tex challenges.tex future.tex differences.tex
	pdflatex paper
	bibtex paper
	pdflatex paper
	pdflatex paper

clean: 
	\rm -f *.dvi *.aux *.ps *~ *.log *.blg *.bbl *.pdf *.ent
