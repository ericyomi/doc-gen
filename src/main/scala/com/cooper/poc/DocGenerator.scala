package com.cooper.poc

import java.io.{File, PrintWriter}

import scala.util.Random

class DocGenerator {
  private val TOTAL_WORDS = 430000
  private val DICTIONARY_SIZE = 5000

  private val docWordIndicesList: Seq[Seq[Int]] = Seq(100, 200, 300, 400, 500, 600, 700, 800, 900, 1000).map { docLength =>
    selectRandom(docLength, DICTIONARY_SIZE)
  }

  private val dictionary = Dictionary(selectRandom(DICTIONARY_SIZE, TOTAL_WORDS))

  def main(args: Array[String]): Unit = {
    val inputFileWriter = new PrintWriter(new File("input.txt"))
    val outputFileWriter = new PrintWriter(new File("output.txt"))
    createDocuments().foreach{ document =>
      inputFileWriter.println(document.wordIndices.mkString("", "   ", ""))
      outputFileWriter.println(document.documentType.documentTypeVector.mkString("", "   ", ""))
    }
    inputFileWriter.close()
    outputFileWriter.close()
  }

  private def createDocuments(): Seq[Document] = {
    docWordIndicesList.zipWithIndex.flatMap { case (docWordIndices, docTypeIndex) =>
      val docLength = docWordIndices.length
      val docTypeVector = documentTypeIndexToVector(docTypeIndex, docWordIndicesList.length)
      val docType = DocumentType(docTypeVector, dictionary, indicesToVector(docWordIndices, DICTIONARY_SIZE))
      ((1 to 20).flatMap { _ =>
        Seq(docLength * 0.01, docLength * 0.02, docLength * 0.03, docLength * 0.04, docLength * 00.4).map { value =>
          val noise: Int = value.toInt
          addRandomWordIndices(removeRandomWordIndices(docWordIndices, noise), noise, DICTIONARY_SIZE)
        }
      } :+ docWordIndices).map { indices =>
        Document(docType, indicesToVector(indices, DICTIONARY_SIZE))
      }
    }
  }

  private def removeRandomWordIndices(docWordIndices: Seq[Int], nToRemove: Int): Seq[Int] = {
    val indicesToRemove = selectRandom(nToRemove, docWordIndices.length)
    docWordIndices.zipWithIndex.filterNot{
      case (_, index) => indicesToRemove.contains(index)
    }.map(_._1)
  }

  private def addRandomWordIndices(docWordIndices: Seq[Int], nToAdd: Int, seed: Int): Seq[Int] = {
    var selections: scala.collection.mutable.Set[Int] = scala.collection.mutable.Set()
    while(selections.size < nToAdd){
      val selection = Random.nextInt(seed)
      if(!selections.contains(selection) && !docWordIndices.contains(selection)){
        selections += selection
      }
    }
    selections.toSeq++:docWordIndices
  }

  private def selectRandom(count: Int, seed: Int): Seq[Int] = {
    var selections: scala.collection.mutable.Set[Int] = scala.collection.mutable.Set()
    while(selections.size < count){
      val selection = Random.nextInt(seed)
      if(!selections.contains(selection)){
        selections += selection
      }
    }
    selections.toSeq
  }

  private def indicesToVector(indices: Seq[Int], dictionarySize: Int): Seq[Int] = {
    (0 until dictionarySize).map{index =>
      if(indices.contains(index)) 1 else 0
    }
  }

  private def documentTypeIndexToVector(documentTypeIndex: Int, numOfDocumentTypes: Int): Seq[Int] = {
    (0 to documentTypeIndex).map { index =>
      if(index == documentTypeIndex) 1 else 0
    }
  }
}

