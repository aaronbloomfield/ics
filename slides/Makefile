SLIDE_SETS: anonymity.html binary-exploits.html buffer-overflows.html cryptocurrency.html encryption.html ethics.html forensics.html introduction.html networks.html policy.html reflection.html rootkits.html security-mindset.html sql-xss-csrf.html stuxnet.html terminology.html viruses.html vms.html web-security.html
.SUFFIXES: .qmd .html
.PHONY: slides

slides: $(SLIDE_SETS)
	@echo conversion done

.qmd.html:
	quarto render $< --to revealjs

clean:
	/bin/rm -rf *~ */*~ */*/*~ */*/*/*~

preview:
