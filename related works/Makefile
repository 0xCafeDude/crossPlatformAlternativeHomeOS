
all: related.pdf

related.pdf: biblio.bib related.tex
	pdflatex related
	bibtex related
	pdflatex related
	pdflatex related

clean: 
	\rm -f *.dvi *.aux *.ps *~ *.log *.blg *.bbl *.pdf *.ent
