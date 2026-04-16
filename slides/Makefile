SLIDE_SETS: anonymity.html binary-exploits.html buffer-overflows.html cryptocurrency.html encryption.html ethics.html forensics.html introduction.html networks.html policy.html reflection.html rootkits.html security-mindset.html sql-xss-csrf.html stuxnet.html terminology.html viruses.html vms.html web-security.html
.SUFFIXES: .qmd .html
.PHONY: slides all clean

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

clean:
	/bin/rm -rf *~ */*~ */*/*~ */*/*/*~

all:
	touch *.qmd
	make $(SLIDE_SETS)

accessibility:
	$(SED) s/", maximum-scale=1.0, user-scalable=no, minimal-ui"// *.html
	/bin/rm -rf *.todel
