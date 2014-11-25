
all: related.pdf related2.pdf

related.pdf: biblio.bib related.tex
	pdflatex related
	bibtex related
	pdflatex related
	pdflatex related
	
related2.pdf: biblio.bib related2.tex
	pdflatex related2
	bibtex related2
	pdflatex related2
	pdflatex related2

clean: 
	\rm -f *.dvi *.aux *.ps *~ *.log *.blg *.bbl *.pdf *.ent
