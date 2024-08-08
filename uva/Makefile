SLIDE_SETS: daily-announcements.html
.SUFFIXES: .qmd .html
.PHONY: slides

slides: $(SLIDE_SETS)
	@echo conversion done

.qmd.html:
	quarto render $< --to revealjs

doc:
	@cd .. && make -s
