ifeq ($(shell uname),Darwin)
        SED=sed -i .todel
else
        SED=sed -i
endif

doc:
	@source-highlight -d *.py
	@$(SED) s/"<html>"/"<html lang='en'>"/g *.py.html
	@/bin/rm -f *.todel
