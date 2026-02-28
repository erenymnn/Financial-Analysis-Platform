Financial Analysis & Trading Platform 🚀
A robust Spring Boot backend application designed for real-time cryptocurrency market data analysis and simulated trading. This platform allows users to manage virtual balances, execute buy/sell orders, and track portfolio performance using live market data.

✨ Key Features
Real-Time Market Data: Integrated with Binance API to fetch and update live cryptocurrency prices.

Trading Engine: Secure execution of BUY and SELL orders with real-time balance and quantity validation.

Portfolio Management: Dynamic calculation of asset quantities, average costs, and current market valuations.

Transaction History: Detailed logging of all historical trades with timestamps and price-at-transaction tracking.

High Precision Finance: Utilizes BigDecimal for all monetary calculations to ensure kurush-perfect accuracy.

🛠 Tech Stack
Java 17+ & Spring Boot 3.x

Spring Data JPA: For seamless database interactions.

PostgreSQL: Reliable relational data storage.

Lombok: To minimize boilerplate code.

Binance API: For fetching live market price tickers.

Jackson: Advanced JSON processing and bidirectional relationship management.

🗄 System Architecture
The system is built on 4 core entities:

User: Manages user identity and wallet balance.

Asset: Represents tradable cryptocurrencies and their live prices.

Portfolio: Maps users to their held assets and quantities.

Transaction: A historical ledger for every buy and sell event.

🚀 Getting Started
Prerequisites
JDK 17 or higher

Maven

PostgreSQL
