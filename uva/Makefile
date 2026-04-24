SLIDE_SETS: daily-announcements.html
.SUFFIXES: .qmd .html
.PHONY: slides

ifeq ($(shell uname),Darwin)
        SED=sed -i .todel
else
        SED=sed -i
endif

slides: $(SLIDE_SETS)
	@echo conversion done

.qmd.html:
	quarto render $< --to revealjs
	@$(SED) s/", maximum-scale=1.0, user-scalable=no, minimal-ui"// $@
	@/bin/rm -rf $@.todel
	@$(SED) s/'<img tabindex="0" class="print"'/'<img tabindex="0" class="print" alt="print icon"'/ $@
	@/bin/rm -rf $@.todel
	@$(SED) s+'<img src="../slides/images/quarto.png" class="slide-logo">'+'<img src="../slides/images/quarto.png" alt="quarto logo" class="slide-logo">'+ $@
	@/bin/rm -rf $@.todel

doc:
	@cd .. && make -s
