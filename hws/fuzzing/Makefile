doc:
	@source-highlight -d *.py

uvicorn:
	@uvicorn server:app --reload --port 5000

fuzzuvi:
	@python3 fuzzer.py -u http://localhost:5000/FUZZ -w common.txt
