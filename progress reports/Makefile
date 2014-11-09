
all: report1.pdf

report1.pdf: biblio.bib report1.tex
	pdflatex report1
	bibtex report1
	pdflatex report1
	pdflatex report1

clean: 
	\rm -f *.dvi *.aux *.ps *~ *.log *.blg *.bbl *.pdf *.ent
