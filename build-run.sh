#!/bin/bash

# ==========================================
# Simple Spring Boot Build & Run Script
# ==========================================

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${BLUE}🚀 Building and starting Spring Boot application...${NC}"

# Step 1: Build the application
echo -e "${BLUE}📦 Building with Maven...${NC}"
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo -e "${RED}❌ Maven build failed!${NC}"
    exit 1
fi

# Step 2: Find and copy the JAR file
JAR_FILE=$(find target -name "*.jar" -not -name "*sources.jar" -not -name "*javadoc.jar" | head -n 1)

if [ -z "$JAR_FILE" ]; then
    echo -e "${RED}❌ JAR file not found!${NC}"
    exit 1
fi

cp "$JAR_FILE" "target/breakfast-app.jar"
echo -e "${GREEN}✅ JAR file ready: target/breakfast-app.jar${NC}"

# Step 3: Stop existing containers (if any)
echo -e "${BLUE}🛑 Stopping existing containers...${NC}"
docker-compose down 2>/dev/null || true

# Step 4: Start Docker services
echo -e "${BLUE}🐳 Starting Docker services...${NC}"
docker-compose up -d

if [ $? -ne 0 ]; then
    echo -e "${RED}❌ Docker Compose failed!${NC}"
    exit 1
fi

# Step 5: Show status
echo -e "${GREEN}✅ Services started successfully!${NC}"
echo ""
echo -e "${GREEN}🌐 Application: http://localhost:8080/api${NC}"
echo ""
echo "📋 View logs: docker-compose logs -f"
echo "🛑 Stop services: docker-compose down"