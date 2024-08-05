SLIDE_SETS: introduction.html
.SUFFIXES: .qmd .html
.PHONY: slides

slides: $(SLIDE_SETS)
	@echo conversion done

.qmd.html:
	quarto render $< --to revealjs

clean:
	/bin/rm -rf *~ */*~ */*/*~ */*/*/*~

preview:

