cd client
npm run build
cd ..
cp -a ./client/build/. ./server/build/resources/main/static
cp -a ./client/build/. ./server/src/main/resources/static
